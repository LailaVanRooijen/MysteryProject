package com.mystery.project.entities.organizationuser;

import com.mystery.project.entities.organization.Organization;
import com.mystery.project.entities.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationUserRepository extends JpaRepository<OrganizationUser, Long> {
  List<OrganizationUser> findByUser(User user);

  Optional<OrganizationUser> findByUserAndOrganization(User user, Organization organization);

  List<OrganizationUser> findByOrganization(Organization organization);
}
