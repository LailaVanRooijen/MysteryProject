package com.mystery.project.entities.organizations;

import com.mystery.project.entities.organizations.dto.GetOrganization;
import com.mystery.project.entities.organizations.dto.PostOrganization;
import com.mystery.project.entities.organizations.usersorganization.OrganizationRole;
import com.mystery.project.entities.organizations.usersorganization.UsersOrganizations;
import com.mystery.project.entities.organizations.usersorganization.UsersOrganizationsRepository;
import com.mystery.project.entities.user.User;
import com.mystery.project.entities.user.UserRepository;
import com.mystery.project.exception.BadRequestException;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService {
  private final OrganizationRepository organizationRepository;
  private final UsersOrganizationsRepository usersOrganizationsRepository;
  private final UserRepository userRepository;

  public GetOrganization create(PostOrganization postOrganization, User user) {
    if (postOrganization == null) throw new BadRequestException("Organisation cannot be null");
    if (user == null) throw new BadRequestException("User cannot be null");

    Organization createdOrganization = PostOrganization.from(postOrganization);
    organizationRepository.save(createdOrganization);

    addUserToOrganization(user, createdOrganization, OrganizationRole.OWNER);

    return GetOrganization.to(createdOrganization);
  }

  public void deleteOrganization(Long id, User user) {
    Organization organization = getOrganization(id);

    if (isNotOwner(user, organization)) {
      throw new BadRequestException("You do not have permission to delete an organization");
    }

    organizationRepository.deleteById(id);
  }

  public void addStudentToOrganization(Long organizationId, User loggedInUser, UUID studentId) {
    Organization organization = getOrganization(organizationId);

    if (isNotOwner(loggedInUser, organization) || !isMember(loggedInUser, organization)) {
      throw new BadRequestException(
          "You do not have permission to add students to this organization.");
    }

    User student = getUser(studentId);
    if (isMember(student, organization)) {
      throw new BadRequestException("Student is already a member");
    }

    addUserToOrganization(student, organization, OrganizationRole.STUDENT);
  }

  /* Helper methods */
  @Transactional
  private void addUserToOrganization(User user, Organization organization, OrganizationRole role) {
    UsersOrganizations joinTable = new UsersOrganizations(user, organization, role);
    usersOrganizationsRepository.save(joinTable);
  }

  private boolean isNotOwner(User user, Organization organization) {
    UsersOrganizations membership =
        usersOrganizationsRepository
            .findByUserAndOrganization(user, organization)
            .orElseThrow(
                () -> new BadRequestException("User is not a member of this organization"));
    return membership.getOrganizationRole() != OrganizationRole.OWNER;
  }

  private UsersOrganizations getMembership(User user, Organization organization) {
    return usersOrganizationsRepository
        .findByUserAndOrganization(user, organization)
        .orElseThrow(
            () -> new BadRequestException("An organisation with this user does not exist"));
  }

  private boolean isMember(User user, Organization organization) {
    UsersOrganizations membership =
        usersOrganizationsRepository.findByUserAndOrganization(user, organization).orElse(null);
    return membership != null;
  }

  private Organization getOrganization(Long id) {
    return organizationRepository
        .findById(id)
        .orElseThrow(() -> new BadRequestException("This organization does not exist"));
  }

  private User getUser(UUID id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new BadRequestException("User does not exist"));
  }
}
