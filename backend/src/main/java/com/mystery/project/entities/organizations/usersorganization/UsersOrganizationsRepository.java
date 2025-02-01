package com.mystery.project.entities.organizations.usersorganization;

import com.mystery.project.entities.organizations.Organization;
import com.mystery.project.entities.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersOrganizationsRepository extends JpaRepository<UsersOrganizations, Long> {
  List<UsersOrganizations> findByUser(User user);

  Optional<UsersOrganizations> findByUserAndOrganization(User user, Organization organization);

  List<UsersOrganizations> findByOrganization(Organization organization);
}
