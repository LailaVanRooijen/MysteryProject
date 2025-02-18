package com.mystery.project.entities.passwordrequest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PasswordResetExceptionHandler {
  @ExceptionHandler(PasswordResetNonExistentEmailException.class)
  public ResponseEntity<ProblemDetail> passwordResetNonExistentEmailHandler() {
    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.OK,
            "If an account with this email is registered, you will receive an email.");
    return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
  }

  @ExceptionHandler(PasswordResetRequestExpiredException.class)
  public ResponseEntity<ProblemDetail> passwordResetRequestExpiredHandler() {
    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST, "Password Reset Request has expired");
    return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
  }

  @ExceptionHandler(PasswordResetRequestNotFound.class)
  public ResponseEntity<ProblemDetail> passwordResetRequestNotFoundHandler() {
    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "No Password reset requested");
    return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
  }

  @ExceptionHandler(UnauthorizedPasswordResetRequestException.class)
  public ResponseEntity<ProblemDetail> unauthorizedPasswordResetRequestHandler() {
    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "invalid credentials");
    return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
  }

  @ExceptionHandler(TooManyPasswordResetRequestsException.class)
  public ResponseEntity<ProblemDetail> TooManyPasswordResetRequestsHandler() {
    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.TOO_MANY_REQUESTS,
            "Too many password reset requests are made, wait at least 60 minutes");
    return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
  }
}
