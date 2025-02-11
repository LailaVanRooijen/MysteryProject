package com.mystery.project.entities.organization;

import com.mystery.project.entities.organization.dto.GetOrganization;
import com.mystery.project.entities.organization.dto.PostOrganization;
import com.mystery.project.entities.organization.userorganization.OrganizationRole;
import com.mystery.project.entities.organization.userorganization.UserOrganization;
import com.mystery.project.entities.organization.userorganization.UserOrganizationRepository;
import com.mystery.project.entities.user.User;
import com.mystery.project.entities.user.UserRepository;
import com.mystery.project.exception.BadRequestException;
import com.mystery.project.exception.EntityNotFoundException;
import com.mystery.project.exception.ForbiddenException;
import com.mystery.project.exception.NoContentException;
import jakarta.transaction.Transactional;
import java.util.List;
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
    if (postOrganization == null) throw new EntityNotFoundException("Organisation cannot be null");
    if (user == null) throw new ForbiddenException();

    Organization createdOrganization = PostOrganization.from(postOrganization);
    organizationRepository.save(createdOrganization);

    addUserToOrganization(user, createdOrganization, OrganizationRole.OWNER);

    return GetOrganization.to(createdOrganization);
  }
  
  public void deleteOrganization(Long id, User loggedInUser) {
    Organization organization = getOrganization(id);
    if (loggedInUser == null) throw new ForbiddenException();

    if (isNotOwner(loggedInUser, organization)) {
      throw new ForbiddenException("You do not have permission to delete an organization");
    }

    organizationRepository.deleteById(id);
  }

  public void addStudentToOrganization(Long organizationId, User loggedInUser, UUID studentId) {
    if (loggedInUser == null) throw new BadRequestException("User cannot be null");
    Organization organization = getOrganization(organizationId);

    if (isNotOwner(loggedInUser, organization) || !isMember(loggedInUser, organization)) {
      throw new ForbiddenException(
          "You do not have permission to add students to this organization.");
    }

    User student = getUser(studentId);
    if (isMember(student, organization)) {
      throw new BadRequestException("Student is already a member");
    }

    addUserToOrganization(student, organization, OrganizationRole.STUDENT);
  }

  public GetOrganization getOrganizationById(Long organizationId, User user) {

    if (user == null) throw new BadRequestException("User cannot be null");

    Organization fetchedOrganization =
        organizationRepository.findById(organizationId).orElseThrow(NoContentException::new);

    return GetOrganization.to(fetchedOrganization);
  }

  public List<GetOrganization> getAllOrganizations(User user) {

    if (user == null) throw new BadRequestException("User cannot be null");

    List<Organization> fetchedOrganizations = organizationRepository.findAll();

    if (fetchedOrganizations.isEmpty()) throw new NoContentException();

    return fetchedOrganizations.stream().map(GetOrganization::to).toList();
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
            .orElseThrow(() -> new ForbiddenException("Only the owner can perform this action"));
    return membership.getOrganizationRole() != OrganizationRole.OWNER;
  }

  private UserOrganization getMembership(User user, Organization organization) {
    return userOrganizationRepository
        .findByUserAndOrganization(user, organization)
        .orElseThrow(() -> new BadRequestException("User is not a member of this organization"));
  }

  private boolean isMember(User user, Organization organization) {
    UserOrganization membership =
        userOrganizationRepository.findByUserAndOrganization(user, organization).orElse(null);
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
