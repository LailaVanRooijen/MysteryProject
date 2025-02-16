package com.mystery.project.entities.organizationuser;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mystery.project.entities.organization.Organization;
import com.mystery.project.entities.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class OrganizationUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "organization_id", nullable = false)
  @JsonBackReference
  private Organization organization;

  @Enumerated(EnumType.STRING)
  private OrganizationUserRole organizationUserRole;

  public OrganizationUser(
      User user, Organization organization, OrganizationUserRole organizationUserRole) {
    this.user = user;
    this.organization = organization;
    this.organizationUserRole = organizationUserRole;
  }
}
