package com.mystery.project.util.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
  private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
  private static final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
  private static final String PASSWORD_REGEX =
      "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
  private static final Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);

  public static boolean isValidEmail(String email) {
    Matcher matcher = emailPattern.matcher(email);
    return matcher.matches();
  }

  public static boolean isValidPassword(String password) {
    Matcher matcher = passwordPattern.matcher(password);
    return matcher.matches();
  }

  public static boolean isValidDisplayName(String displayName) {
    return displayName.length() >= 3;
  }
}
