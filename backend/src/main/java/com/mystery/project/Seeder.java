package com.mystery.project;

import com.mystery.project.authentication.AuthenticationService;
import com.mystery.project.authentication.dtos.RegisterRequestDto;
import com.mystery.project.entities.courses.CourseService;
import com.mystery.project.entities.courses.dto.PostCourse;
import com.mystery.project.entities.organization.Organization;
import com.mystery.project.entities.organization.OrganizationRepository;
import com.mystery.project.entities.organization.OrganizationService;
import com.mystery.project.entities.organization.dto.PostOrganization;
import com.mystery.project.entities.user.User;
import com.mystery.project.entities.user.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {
  private final AuthenticationService authenticationService;
  private final UserRepository userRepository;
  private final OrganizationService organizationService;
  private final CourseService courseService;
  private final OrganizationRepository organizationRepository;

  @Override
  public void run(String... args) throws Exception {
    seedUsers();
    seedOrganizations();
    seedCourses();
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
        new PostOrganization("Chadette Industries"),
        userRepository.findByDisplayNameIgnoreCase("chadette").orElseThrow());
  }

  private void seedCourses() {
    List<User> users = userRepository.findAll();
    List<Organization> organizations = organizationRepository.findAll();

    UUID teacherId = users.get(0).getId();
    Long organizationId = organizations.get(0).getId();

    courseService.create(
        new PostCourse("Java Course", "Everything you need to know about Java."),
        organizationId,
        teacherId);

    courseService.create(
        new PostCourse("Typescript: Zero to Hero", "Everything you need to know about TypeScript."),
        organizationId,
        teacherId);
  }
}
