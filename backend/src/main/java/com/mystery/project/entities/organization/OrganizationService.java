package com.mystery.project.entities.organization;

import com.mystery.project.entities.organization.dto.GetOrganization;
import com.mystery.project.entities.organization.dto.PostOrganization;
import com.mystery.project.entities.organization.userorganization.OrganizationRole;
import com.mystery.project.entities.organization.userorganization.UserOrganization;
import com.mystery.project.entities.organization.userorganization.UserOrganizationRepository;
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
  private final UserOrganizationRepository userOrganizationRepository;
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
    UserOrganization joinTable = new UserOrganization(user, organization, role);
    userOrganizationRepository.save(joinTable);
  }

  private boolean isNotOwner(User user, Organization organization) {
    UserOrganization membership =
        userOrganizationRepository
            .findByUserAndOrganization(user, organization)
            .orElseThrow(
                () -> new BadRequestException("User is not a member of this organization"));
    return membership.getOrganizationRole() != OrganizationRole.OWNER;
  }

  private UserOrganization getMembership(User user, Organization organization) {
    return userOrganizationRepository
        .findByUserAndOrganization(user, organization)
        .orElseThrow(
            () -> new BadRequestException("An organisation with this user does not exist"));
  }

  private boolean isMember(User user, Organization organization) {
    UserOrganization membership =
        userOrganizationRepository.findByUserAndOrganization(user, organization).orElse(null);
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
