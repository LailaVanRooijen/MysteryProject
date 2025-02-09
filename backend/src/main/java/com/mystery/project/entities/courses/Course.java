package com.mystery.project.entities.courses;

import com.mystery.project.entities.organization.Organization;
import com.mystery.project.entities.user.User;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "courses")
@Getter
@Setter
@NoArgsConstructor
public class Course {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  @Setter
  private String name;

  @Column(nullable = true)
  @Setter
  private String description;

  @ManyToOne private Organization organization;

  @ManyToOne private User teacher;

  @ManyToMany
  @JoinTable(
      name = "course_students",
      joinColumns = @JoinColumn(name = "course_id"),
      inverseJoinColumns = @JoinColumn(name = "student_id"))
  private final List<User> students = new ArrayList<>();

  public Course(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public Course(String name, String description, Organization organization, User teacher) {
    this.name = name;
    this.description = description;
    this.organization = organization;
    this.teacher = teacher;
  }
}
