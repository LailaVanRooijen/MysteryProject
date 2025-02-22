package com.mystery.project.entities.passwordrequest;

import com.mystery.project.entities.passwordrequest.dto.PatchUserPassword;
import com.mystery.project.entities.passwordrequest.dto.RequestPasswordReset;
import com.mystery.project.mainconfiguration.Routes;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(Routes.RESET_PASSWORD)
public class PasswordResetController {
  private final PasswordResetService passwordResetService;

  @PatchMapping("/{resetId}")
  public ResponseEntity<Void> updatePassword(
      @PathVariable UUID resetId, @RequestBody @Valid PatchUserPassword patch) {
    passwordResetService.patchUserPassword(resetId, patch);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/reset")
  public ResponseEntity<ProblemDetail> resetPassword(
      @RequestBody @Valid RequestPasswordReset request) {
    passwordResetService.resetPassword(request);
    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.OK,
            "If an account with this email is registered, you will receive an email.");
    return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
  }
}
