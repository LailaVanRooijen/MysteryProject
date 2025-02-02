package com.mystery.project.entities.organizations.usersorganization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersOrganisationsRepository extends JpaRepository<UsersOrganizations, Long> {}
