package com.mystery.project.entities.courses;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

  List<Course> findByOrganizationId(Long organizationId, Pageable pageable);
}
