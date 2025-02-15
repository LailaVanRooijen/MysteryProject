package com.mystery.project;

import com.mystery.project.entities.courses.Course;
import com.mystery.project.entities.courses.CourseRepository;
import com.mystery.project.entities.organization.Organization;
import com.mystery.project.entities.organization.OrganizationRepository;
import com.mystery.project.entities.organizationuser.OrganizationUser;
import com.mystery.project.entities.organizationuser.OrganizationUserRepository;
import com.mystery.project.entities.organizationuser.OrganizationUserRole;
import com.mystery.project.entities.user.Role;
import com.mystery.project.entities.user.User;
import com.mystery.project.entities.user.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {
  private final UserRepository userRepository;
  private final OrganizationRepository organizationRepository;
  private final OrganizationUserRepository organizationUserRepository;
  private final CourseRepository courseRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    seedUsers();
    seedOrganizations();
    seedUserOrganizations();
    seedCourses();
  }

  private void seedCourses() {
    if (!courseRepository.findAll().isEmpty()) return;

    List<User> users = userRepository.findAll();
    if (users.isEmpty() || users.size() < 2) return;
    List<Organization> organizations = organizationRepository.findAll();
    if (organizations.isEmpty() || organizations.size() < 2) return;

    Course courseJava =
        new Course(
            "Java course",
            "Everything you need to know about Java.",
            organizations.get(0),
            users.get(0));
    Course courseTypescript =
        new Course(
            "Typescript: Zero to Hero",
            "Everything you need to know about Typescript.",
            organizations.get(1),
            users.get(1));

    courseRepository.saveAll(List.of(courseJava, courseTypescript));
  }

  @Transactional
  private void seedUserOrganizations() {
    if (!organizationUserRepository.findAll().isEmpty()) return;

    List<User> users = userRepository.findAll();
    if (users.isEmpty() || users.size() < 2) return;
    List<Organization> organizations = organizationRepository.findAll();
    if (organizations.isEmpty() || organizations.size() < 2) return;

    OrganizationUser membership1 =
        new OrganizationUser(users.get(0), organizations.get(0), OrganizationUserRole.OWNER);
    OrganizationUser membership2 =
        new OrganizationUser(users.get(1), organizations.get(1), OrganizationUserRole.OWNER);

    organizationUserRepository.saveAll(List.of(membership1, membership2));
  }

  private void seedOrganizations() {
    if (!organizationRepository.findAll().isEmpty()) return;

    Organization medeyu = new Organization("Medeyu");
    Organization shareskill = new Organization("ShareSkill");
    organizationRepository.saveAll(List.of(medeyu, shareskill));
  }

  private void seedUsers() {
    if (!userRepository.findAll().isEmpty()) return;

    User admin = new User("admin@gmail.com", passwordEncoder.encode("Password123!"), "Admin", Role.ADMIN);
    User regularUser1 = new User("JaneElliot@gmail.com", passwordEncoder.encode("Password123!"), "JaneElliot", Role.USER);
    User regularUser2 =
        new User("MartinFowler@gmail.com", passwordEncoder.encode("Password123!"), "MartinFowler", Role.USER);
    userRepository.saveAll(List.of(admin, regularUser1, regularUser2));
  }
}
