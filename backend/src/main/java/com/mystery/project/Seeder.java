package com.mystery.project;

import com.mystery.project.authentication.AuthenticationService;
import com.mystery.project.authentication.dtos.RegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {
  private final AuthenticationService authenticationService;

  @Override
  public void run(String... args) throws Exception {
    seedUsers();
  }

  private void seedUsers() {
    authenticationService.registerUser(
        new RegisterRequestDto("chad@gmail.com", "Password123!", "chad"));
    authenticationService.registerUser(
        new RegisterRequestDto("chadette@gmail.com", "Password123!", "chadette"));
  }
}
