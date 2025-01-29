package com.mystery.project.authentication.exceptions;

public class EmailNotFoundException extends RuntimeException {
  public EmailNotFoundException(String message) {
    super(message);
  }
}
