package com.mystery.project.entities.courses.dto;

import com.mystery.project.entities.courses.Course;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class PostCourse {
  @NotBlank(message = "Name can not be blank")
  @Length(max = 255, message = "Name is too long")
  private String name;

  @NotBlank(message = "Description can not be blank")
  @Length(max = 255, message = "Description is too long")
  private String description;

  public static Course from(PostCourse dto) {
    return new Course(dto.name, dto.description);
  }
}
