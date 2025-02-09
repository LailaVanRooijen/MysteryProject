package com.mystery.project.entities.organizations;

import com.mystery.project.entities.organizations.dto.GetOrganization;
import com.mystery.project.entities.organizations.dto.PostOrganization;
import com.mystery.project.entities.organizations.usersorganization.OrganizationRole;
import com.mystery.project.entities.organizations.usersorganization.UsersOrganisationsRepository;
import com.mystery.project.entities.organizations.usersorganization.UsersOrganizations;
import com.mystery.project.entities.user.User;
import com.mystery.project.exception.BadRequestException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService {
  private final OrganizationRepository organizationRepository;
  private final UsersOrganisationsRepository usersOrganisationsRepository;

  public GetOrganization create(PostOrganization postOrganization, User user) {
    if (postOrganization == null) throw new BadRequestException("Organisation cannot be null");
    if (user == null) throw new BadRequestException("User cannot be null");

    Organization createdOrganization = PostOrganization.from(postOrganization);
    organizationRepository.save(createdOrganization);

    addUserToOrganisation(user, createdOrganization, OrganizationRole.OWNER);

    return GetOrganization.to(createdOrganization);
  }

  public Optional<Organization> getById(Long id) {
    return organizationRepository.findById(id);
  }

  @Transactional
  private void addUserToOrganisation(User user, Organization organization, OrganizationRole role) {
    UsersOrganizations joinTable = new UsersOrganizations(user, organization, role);
    usersOrganisationsRepository.save(joinTable);
  }
}
