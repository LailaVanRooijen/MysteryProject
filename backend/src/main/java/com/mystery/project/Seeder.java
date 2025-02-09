package com.mystery.project;

import com.mystery.project.authentication.AuthenticationService;
import com.mystery.project.authentication.dtos.RegisterRequestDto;
import com.mystery.project.entities.organizations.OrganizationRepository;
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
  private final OrganizationRepository organizationRepository;

  @Override
  public void run(String... args) throws Exception {
    seedUsers();
    seedOrganizations();
  }

  private void seedUsers() {
    if (!userRepository.findAll().isEmpty()) return;

    authenticationService.registerUser(
        new RegisterRequestDto("test@gmail.com", "Password123!", "test"));
    authenticationService.registerUser(
        new RegisterRequestDto("Hermione@gmail.com", "Password123!", "Hermione"));
    authenticationService.registerUser(
        new RegisterRequestDto("Ron@gmail.com", "Password123!", "Ron"));
  }

  private void seedOrganizations() {
    if (!organizationRepository.findAll().isEmpty()) return;
    organizationService.create(
        new PostOrganization("Organization A"),
        userRepository.findByDisplayNameIgnoreCase("Hermione").orElseThrow());

    organizationService.create(
        new PostOrganization("Organization B"),
        userRepository.findByDisplayNameIgnoreCase("Ron").orElseThrow());
  }
}
