package com.mystery.project.entities.organizations;

import com.mystery.project.entities.user.User;
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

  @OneToMany private final List<User> users = new ArrayList<>();

  public Organization(String name) {
    this.name = name;
  }
}
