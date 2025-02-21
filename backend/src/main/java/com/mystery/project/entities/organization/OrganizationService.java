package com.mystery.project.entities.organization;

import com.mystery.project.entities.organization.dto.PostOrganization;
import com.mystery.project.entities.organizationuser.OrganizationUser;
import com.mystery.project.entities.organizationuser.OrganizationUserRepository;
import com.mystery.project.entities.organizationuser.OrganizationUserRole;
import com.mystery.project.entities.user.User;
import com.mystery.project.entities.user.UserRepository;
import com.mystery.project.exception.BadRequestException;
import com.mystery.project.exception.EntityNotFoundException;
import com.mystery.project.exception.ForbiddenException;
import com.mystery.project.exception.NoContentException;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService {
  private final OrganizationRepository organizationRepository;
  private final OrganizationUserRepository organizationUserRepository;
  private final UserRepository userRepository;

  @Transactional
  public Organization create(PostOrganization postOrganization, User user) {
    Organization createdOrganization = PostOrganization.from(postOrganization);
    organizationRepository.save(createdOrganization);

    addUserToOrganization(user, createdOrganization, OrganizationUserRole.OWNER);

    return createdOrganization;
  }

  public void deleteOrganization(Long id, User loggedInUser) {
    Organization organization = getOrganization(id);

    if (!isOwner(loggedInUser, organization)) {
      throw new ForbiddenException("You do not have permission to delete an organization");
    }

    organizationRepository.deleteById(id);
  }

  public void addStudentToOrganization(Long organizationId, User loggedInUser, UUID studentId) {
    Organization organization = getOrganization(organizationId);
    User student = getUser(studentId);

    if (!isOwner(loggedInUser, organization) || !isMember(loggedInUser, organization)) {
      throw new ForbiddenException(
          "You do not have permission to add students to this organization.");
    }

    if (isMember(student, organization)) {
      throw new BadRequestException("Student is already a member");
    }

    addUserToOrganization(student, organization, OrganizationUserRole.STUDENT);
  }

  public void removeStudentFromOrganization(
      Long organizationId, User loggedInUser, UUID studentId) {
    Organization organization = getOrganization(organizationId);
    User student = getUser(studentId);

    if (!isOwner(loggedInUser, organization)) {
      throw new ForbiddenException("Only the owner can perform this action");
    }
    if (isOwner(student, organization)) {
      throw new ForbiddenException("The owner may not be removed from the organization");
    }
    if (!isMember(student, organization)) {
      throw new BadRequestException("This student is not a member of this organization");
    }

    removeUserFromOrganization(student, organization);
  }

  public Organization getById(Long organizationId, User user) {
    if (user == null) throw new BadRequestException("User cannot be null");
    return organizationRepository.findById(organizationId).orElseThrow(NoContentException::new);
  }

  public Page<Organization> getAll(Pageable pageable) {
    return organizationRepository.findAll(pageable);
  }

  /* Helper methods */
  @Transactional
  private void addUserToOrganization(
      User user, Organization organization, OrganizationUserRole role) {
    OrganizationUser membership = new OrganizationUser(user, organization, role);
    organizationUserRepository.save(membership);
  }

  @Transactional
  private void removeUserFromOrganization(User student, Organization organization) {
    OrganizationUser membership =
        organizationUserRepository
            .findByUserAndOrganization(student, organization)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "User is not part of this organization, or organization does not exist"));
    organizationUserRepository.deleteById(membership.getId());
  }

  private boolean isOwner(User user, Organization organization) {
    OrganizationUser membership = findMembership(user, organization);
    return membership.getOrganizationUserRole() == OrganizationUserRole.OWNER;
  }

  private OrganizationUser findMembership(User user, Organization organization) {
    return organizationUserRepository
        .findByUserAndOrganization(user, organization)
        .orElseThrow(
            () ->
                new BadRequestException(
                    "Can not find membership, either user is not a member or organization does not exist"));
  }

  private boolean isMember(User user, Organization organization) {
    OrganizationUser membership =
        organizationUserRepository.findByUserAndOrganization(user, organization).orElse(null);
    return membership != null;
  }

  private Organization getOrganization(Long id) {
    return organizationRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("This organization does not exist"));
  }

  private User getUser(UUID id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User does not exist"));
  }
}
