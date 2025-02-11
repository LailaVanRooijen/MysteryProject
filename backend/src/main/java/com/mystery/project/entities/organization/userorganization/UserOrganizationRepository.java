package com.mystery.project.entities.organization.userorganization;

import com.mystery.project.entities.organization.Organization;
import com.mystery.project.entities.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrganizationRepository extends JpaRepository<UserOrganization, Long> {
  List<UserOrganization> findByUser(User user);

  Optional<UserOrganization> findByUserAndOrganization(User user, Organization organization);

  List<UserOrganization> findByOrganization(Organization organization);
}
