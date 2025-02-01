package com.mystery.project.entities.organizations.usersorganization;

import com.mystery.project.entities.organizations.Organization;
import com.mystery.project.entities.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UsersOrganizations {
  @Id @GeneratedValue private Long id;

  @ManyToOne private User user;
  @ManyToOne private Organization organization;
  private OrganizationRole organizationRole;

  public UsersOrganizations(
      User user, Organization organization, OrganizationRole organizationRole) {
    this.user = user;
    this.organization = organization;
    this.organizationRole = organizationRole;
  }
}
