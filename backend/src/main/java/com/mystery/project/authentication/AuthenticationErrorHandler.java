package com.mystery.project.authentication;

import com.mystery.project.authentication.exceptions.DuplicateDisplayNameException;
import com.mystery.project.authentication.exceptions.DuplicateEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class AuthenticationErrorHandler {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AuthorizationDeniedException.class, InternalAuthenticationServiceException.class})
    public String authorizationDenied(Exception e) {
        return "You are not authorized. " + e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DuplicateDisplayNameException.class, DuplicateEmailException.class})
    public String duplicateCredentials(Exception e) {
        return "An account with those credentials already exists. " + e.getMessage();
    }
}
