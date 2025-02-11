package com.mystery.project.entities.courses.dto;

import com.mystery.project.entities.courses.Course;

public record PostCourse(String name, String description) {
  public static Course from(PostCourse dto) {
    return new Course(dto.name, dto.description);
  }
}
