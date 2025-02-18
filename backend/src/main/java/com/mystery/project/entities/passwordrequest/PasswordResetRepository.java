package com.mystery.project.entities.passwordrequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {
  Optional<PasswordReset> findByResetIdAndUserId(UUID resetId, UUID userId);

  List<PasswordReset> findByUserId(UUID id);
}
