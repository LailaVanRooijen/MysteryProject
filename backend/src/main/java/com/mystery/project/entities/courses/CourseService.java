package com.mystery.project.entities.courses;

import com.mystery.project.entities.courses.dto.PostCourse;
import com.mystery.project.entities.organization.Organization;
import com.mystery.project.entities.organization.OrganizationRepository;
import com.mystery.project.entities.user.User;
import com.mystery.project.entities.user.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {
  private final CourseRepository courseRepository;
  private final OrganizationRepository organizationRepository;
  private final UserRepository userRepository;

  public List<Course> getByOrganization(Long organizationId, Pageable pageable) {
    return courseRepository.findByOrganizationId(organizationId, pageable);
  }

  public Course create(PostCourse dto, Long organizationId, UUID teacherId) {
    Organization organization =
        organizationRepository
            .findById(organizationId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid organization ID"));

    User teacher =
        userRepository
            .findById(teacherId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid teacher ID"));

    Course course = PostCourse.from(dto);

    course.setOrganization(organization);
    course.setTeacher(teacher);

    return courseRepository.save(course);
  }
}
