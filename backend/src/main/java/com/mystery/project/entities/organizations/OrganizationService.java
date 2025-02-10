package com.mystery.project.entities.organizations;

import com.mystery.project.entities.organizations.dto.GetOrganization;
import com.mystery.project.entities.organizations.Organization;
import com.mystery.project.entities.organizations.dto.PostOrganization;
import com.mystery.project.entities.organizations.usersorganization.OrganizationRole;
import com.mystery.project.entities.organizations.usersorganization.UsersOrganisationsRepository;
import com.mystery.project.entities.organizations.usersorganization.UsersOrganizations;
import com.mystery.project.entities.user.User;
import com.mystery.project.exception.BadRequestException;
import com.mystery.project.exception.NoContentException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

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

  @Transactional
  private void addUserToOrganisation(User user, Organization organization, OrganizationRole role) {
    UsersOrganizations joinTable = new UsersOrganizations(user, organization, role);
    usersOrganisationsRepository.save(joinTable);
  }

  public GetOrganization getOrganizationById(Long organizationId,User user) {

    if (user == null) throw new BadRequestException("User cannot be null");

      Organization fetchedOrganization =
          organizationRepository
              .findById(organizationId)
              .orElseThrow(
                  () -> new NoContentException());

    return GetOrganization.to(fetchedOrganization);
  }

  public List<GetOrganization> getAllOrganizations(User user){

      if (user == null) throw new BadRequestException("User cannot be null");

      List<Organization> fetchedOrganizations = organizationRepository.findAll();

      if(fetchedOrganizations.isEmpty())throw new NoContentException();    

      return fetchedOrganizations.stream().map(GetOrganization::to).toList();
  }
}
