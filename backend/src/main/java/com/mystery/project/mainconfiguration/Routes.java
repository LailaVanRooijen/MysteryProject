package com.mystery.project.mainconfiguration;

public class Routes {
  public static final String BASE_ROUTE = "/api/v1";
  public static final String AUTHENTICATION = BASE_ROUTE + "/auth";
  public static final String ORGANIZATIONS = BASE_ROUTE + "/organizations";
  public static final String COURSES = ORGANIZATIONS + "/{organizationId}" + "/courses";
  public static final String USERS = BASE_ROUTE + "/users";
  public static final String RESET_PASSWORD = BASE_ROUTE + "/password";
}
