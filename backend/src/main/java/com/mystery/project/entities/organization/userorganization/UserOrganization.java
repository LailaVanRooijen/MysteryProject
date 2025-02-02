package com.mystery.project.entities.organization.userorganization;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mystery.project.entities.organization.Organization;
import com.mystery.project.entities.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserOrganization {
  @Id @GeneratedValue private Long id;

  @ManyToOne private User user;
  @ManyToOne @JsonBackReference private Organization organization;
  private OrganizationRole organizationRole;

  public UserOrganization(User user, Organization organization, OrganizationRole organizationRole) {
    this.user = user;
    this.organization = organization;
    this.organizationRole = organizationRole;
  }
}
