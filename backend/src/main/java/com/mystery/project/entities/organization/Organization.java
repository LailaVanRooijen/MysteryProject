package com.mystery.project.entities.organization;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mystery.project.entities.organizationuser.OrganizationUser;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "organizations")
public class Organization {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  @Setter
  private String name;

  @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private final List<OrganizationUser> organizationUser = new ArrayList<>();

  public Organization(String name) {
    this.name = name;
  }
}
