package com.mystery.project.repositories;

import com.mystery.project.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  User findByDisplayName(String displayName);

  User findByEmail(String email);
}
