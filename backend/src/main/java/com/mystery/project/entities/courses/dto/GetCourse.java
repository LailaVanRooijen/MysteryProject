package com.mystery.project.entities.courses.dto;

import com.mystery.project.entities.courses.Course;
import lombok.Data;

@Data
public class GetCourse {
  private Long id;
  private String name;
  private String description;

  public GetCourse(Course course) {
    this.id = course.getId();
    this.name = course.getName();
    this.description = course.getDescription();
  }
}
