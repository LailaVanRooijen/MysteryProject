package com.mystery.project.entities.passwordrequest;

import com.mystery.project.entities.email.CreateEmailHtmlPages;
import com.mystery.project.entities.email.EmailService;
import com.mystery.project.entities.passwordrequest.dto.PatchUserPassword;
import com.mystery.project.entities.passwordrequest.dto.RequestPasswordReset;
import com.mystery.project.entities.passwordrequest.exceptions.*;
import com.mystery.project.entities.user.User;
import com.mystery.project.entities.user.UserRepository;
import com.mystery.project.exception.BadRequestException;
import com.mystery.project.exception.EntityNotFoundException;
import com.mystery.project.mainconfiguration.Routes;
import com.mystery.project.util.validation.UserCredentialsValidator;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PasswordResetService {
  private final UserRepository userRepository;
  private final EmailService emailService;
  private final PasswordEncoder passwordEncoder;
  private final PasswordResetRepository passwordResetRepository;

  public void patchUserPassword(UUID resetId, PatchUserPassword patch) {
    User user =
        userRepository
            .findByEmailIgnoreCase(patch.email())
            .orElseThrow(EntityNotFoundException::new);

    PasswordReset passwordReset =
        passwordResetRepository
            .findByResetIdAndUserId(resetId, user.getId())
            .orElseThrow(PasswordResetRequestNotFound::new);

    if (passwordReset.isExpired()) throw new PasswordResetRequestExpiredException();

    // Patch also asks for email, so double check if users are the same
    if (!user.equals(passwordReset.getUser())) {
      throw new UnauthorizedPasswordResetRequestException();
    }

    if (UserCredentialsValidator.isValidPasswordPattern(patch.password())) {
      throw new BadRequestException("Password doesn't meet requirements");
    }

    user.setPassword(passwordEncoder.encode(patch.password()));
    passwordReset.setExpired(true);

    passwordResetRepository.save(passwordReset);
    userRepository.save(user);
  }

  public void resetPassword(RequestPasswordReset request) {
    User user =
        userRepository
            .findByEmailIgnoreCase(request.email())
            .orElseThrow(PasswordResetNonExistentEmailException::new);

    deleteExpiredResetRequests(user.getId());
    List<PasswordReset> resetRequests = passwordResetRepository.findByUserId(user.getId());

    if (resetRequests.size() >= 5) throw new TooManyPasswordResetRequestsException();

    PasswordReset passwordReset = new PasswordReset(user);
    passwordResetRepository.save(passwordReset);

    UUID resetId = passwordReset.getResetId();
    String link = generatePasswordResetLink(resetId);

    String emailBody = CreateEmailHtmlPages.generatePasswordResetEmail(link);
    emailService.sendEmail(request.email(), "Password reset", emailBody);
  }

  /*  ~~~ Helper Methods ~~~  */
  public String generatePasswordResetLink(UUID resetId) {
    return Routes.USERS + "/password/" + resetId;
  }

  public void deleteExpiredResetRequests(UUID userId) {
    List<PasswordReset> resetRequests = passwordResetRepository.findByUserId(userId);
    resetRequests.forEach(
        resetRequest -> {
          if (resetRequest.isExpired()) {
            passwordResetRepository.deleteById(resetRequest.getId());
          }
        });
  }
}
