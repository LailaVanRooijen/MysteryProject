package com.mystery.project.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ForbiddenException extends RuntimeException {
  public ForbiddenException(String message) {
    super(message);
  }
}
