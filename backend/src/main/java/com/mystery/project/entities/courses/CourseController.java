package com.mystery.project.entities.courses;

import com.mystery.project.entities.courses.dto.GetCourse;
import com.mystery.project.entities.courses.dto.PostCourse;
import com.mystery.project.entities.organization.OrganizationService;
import com.mystery.project.entities.user.User;
import com.mystery.project.mainconfiguration.Routes;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
  public final OrganizationService organizationService;

  @GetMapping
  public ResponseEntity<List<GetCourse>> getOrganizationCourses(
      @PathVariable Long organizationId, Authentication authentication) {

    List<Course> courses = courseService.getByOrganization(organizationId);
    List<GetCourse> dtoCourses = courses.stream().map(course -> new GetCourse(course)).toList();

    return ResponseEntity.ok(dtoCourses);
  }

  @PostMapping
  public ResponseEntity<GetCourse> createCourse(
      Authentication authentication,
      @PathVariable Long organizationId,
      @RequestBody PostCourse postCourse) {
    User loggedInUser = (User) authentication.getPrincipal();

    Course course = courseService.create(postCourse, organizationId, loggedInUser.getId());
    GetCourse courseDto = new GetCourse(course);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(courseDto.getId())
            .toUri();

    return ResponseEntity.created(location).body(courseDto);
  }
}
