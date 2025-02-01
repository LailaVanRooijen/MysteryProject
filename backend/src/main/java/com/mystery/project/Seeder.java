package com.mystery.project;

import com.mystery.project.authentication.AuthenticationService;
import com.mystery.project.authentication.dtos.RegisterRequestDto;
import com.mystery.project.entities.organizations.OrganizationService;
import com.mystery.project.entities.organizations.dto.PostOrganization;
import com.mystery.project.entities.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {
  private final AuthenticationService authenticationService;
  private final UserRepository userRepository;
  private final OrganizationService organizationService;

  @Override
  public void run(String... args) throws Exception {
    seedUsers();
    seedOrganizations();
  }

  private void seedUsers() {
    authenticationService.registerUser(
        new RegisterRequestDto("test@gmail.com", "Password123!", "test"));
    authenticationService.registerUser(
        new RegisterRequestDto("chad@gmail.com", "Password123!", "chad"));
    authenticationService.registerUser(
        new RegisterRequestDto("chadette@gmail.com", "Password123!", "chadette"));
  }

  private void seedOrganizations() {
    organizationService.create(
        new PostOrganization("Chad Industries"),
        userRepository.findByDisplayNameIgnoreCase("chad").orElseThrow());

    organizationService.create(
        new PostOrganization("Chadette Industries"),
        userRepository.findByDisplayNameIgnoreCase("chadette").orElseThrow());
  }
}
