package com.mystery.project.entities.passwordrequest;

import com.mystery.project.entities.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class PasswordReset {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne private User user;
  private UUID resetId;

  @Column(nullable = false)
  private LocalDateTime createdOn;

  @Column(nullable = false)
  @Setter
  private boolean isExpired;

  public PasswordReset(User user) {
    this.user = user;
    resetId = UUID.randomUUID();
    createdOn = LocalDateTime.now();
  }

  public boolean isExpired() {
    if (this.isExpired) return true;

    boolean isExpiredStatus = createdOn.plusMinutes(60).isBefore(LocalDateTime.now());
    isExpired = isExpiredStatus;
    return isExpiredStatus;
  }
}
