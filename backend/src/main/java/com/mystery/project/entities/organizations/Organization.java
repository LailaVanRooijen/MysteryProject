package com.mystery.project.entities.organizations;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mystery.project.entities.organizations.usersorganization.UsersOrganizations;
import com.mystery.project.exception.BadRequestException;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Organization {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  @Setter
  private String name;

  @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private final List<UsersOrganizations> usersOrganizations = new ArrayList<>();

  public Organization(String name) {
    this.name = name;
  }
}
