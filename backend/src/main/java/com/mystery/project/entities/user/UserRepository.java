package com.mystery.project.entities.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByDisplayNameIgnoreCase(String displayName);

  Optional<User> findByEmailIgnoreCase(String email);
}
