package com.mystery.project.authentication;

import com.mystery.project.authentication.dtos.LoginRequestDto;
import com.mystery.project.authentication.dtos.RegisterRequestDto;
import com.mystery.project.authentication.exceptions.DuplicateDisplayNameException;
import com.mystery.project.authentication.exceptions.DuplicateEmailException;
import com.mystery.project.authentication.exceptions.EmailNotFoundException;
import com.mystery.project.entities.user.Role;
import com.mystery.project.entities.user.User;
import com.mystery.project.entities.user.UserRepository;
import com.mystery.project.exception.BadRequestException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
  private static final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
  private static final String PASSWORD_REGEX =
      "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
  private static final Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);

  public Authentication registerUser(RegisterRequestDto registerRequestDto) {
    if (!isValidEmail(registerRequestDto.email())) {
      throw new BadRequestException("Not a valid email address.");
    }

    if (!isValidPassword(registerRequestDto.password())) {
      throw new BadRequestException(
          "Password must be at least 8 characters long, contain at least one letter, one number, and one special character.");
    }

    if (registerRequestDto.displayName().length() <= 3) {
      throw new BadRequestException("Display name should have at least 3 characters");
    }

    User nameCheck =
        userRepository.findByDisplayNameIgnoreCase(registerRequestDto.displayName()).orElse(null);
    if (nameCheck != null) {
      throw new DuplicateDisplayNameException("Display name is taken.");
    }

    User emailCheck = userRepository.findByEmailIgnoreCase(registerRequestDto.email()).orElse(null);
    if (emailCheck != null) {
      throw new DuplicateEmailException("Email is already registered.");
    }

    userRepository.save(
        new User(
            UUID.randomUUID(),
            registerRequestDto.email(),
            passwordEncoder.encode(registerRequestDto.password()),
            registerRequestDto.displayName(),
            Role.USER));

    Authentication authenticationToken =
        new UsernamePasswordAuthenticationToken(
            registerRequestDto.email(), registerRequestDto.password());
    return authenticationManager.authenticate(authenticationToken);
  }

  public Authentication loginUser(LoginRequestDto loginRequestDto) {
    Authentication authenticationRequest =
        new UsernamePasswordAuthenticationToken(
            loginRequestDto.email(), loginRequestDto.password());
    return authenticationManager.authenticate(authenticationRequest);
  }

  public User getUserByEmail(String email) {
    return userRepository
        .findByEmailIgnoreCase(email)
        .orElseThrow(
            () -> new EmailNotFoundException("A user with this email address does not exist."));
  }

  public boolean isValidEmail(String email) {
    Matcher matcher = emailPattern.matcher(email);
    return matcher.matches();
  }

  public boolean isValidPassword(String password) {
    Matcher matcher = passwordPattern.matcher(password);
    return matcher.matches();
  }
}
