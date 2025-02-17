package com.mystery.project.entities.courses;

import com.mystery.project.entities.courses.dto.GetCourse;
import com.mystery.project.entities.courses.dto.PostCourse;
import com.mystery.project.entities.user.User;
import com.mystery.project.mainconfiguration.Routes;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(Routes.COURSES)
@CrossOrigin(origins = "${spring.frontend-client}")
@RequiredArgsConstructor
public class CourseController {
  public final CourseService courseService;

  @GetMapping
  public ResponseEntity<List<GetCourse>> getOrganizationCourses(
      @PathVariable Long organizationId, Authentication authentication, Pageable pageable) {

    List<Course> courses = courseService.getByOrganization(organizationId, pageable);
    List<GetCourse> dtoCourses = courses.stream().map(GetCourse::new).toList();

    return ResponseEntity.ok(dtoCourses);
  }

  @GetMapping("/{courseId}")
  public ResponseEntity<GetCourse> getOrganizationCourse(
      @PathVariable Long courseId, @PathVariable Long organizationId) {

    Course course = courseService.getOrganizationCourse(organizationId, courseId);

    if (course == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    GetCourse courseDto = new GetCourse(course);

    return ResponseEntity.ok(courseDto);
  }

  @PostMapping
  public ResponseEntity<GetCourse> createCourse(
      Authentication authentication,
      @PathVariable Long organizationId,
      @Valid @RequestBody PostCourse postCourse) {

    User teacher = (User) authentication.getPrincipal();

    Course course = courseService.create(postCourse, organizationId, teacher.getId());
    GetCourse courseDto = new GetCourse(course);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(courseDto.getId())
            .toUri();

    return ResponseEntity.created(location).body(courseDto);
  }
}
