package com.mystery.project.entities.organization.dto;

import static org.junit.jupiter.api.Assertions.*;

import com.mystery.project.entities.organization.Organization;
import com.mystery.project.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostOrganizationTest {
  private PostOrganization postOrganization;

  @BeforeEach
  void setUp() {
    postOrganization = new PostOrganization("Test Organization");
  }

  @Test
  void should_create_entity_from_dto() {
    Organization organization = PostOrganization.from(postOrganization);
    assertEquals(organization.getName(), postOrganization.name());
  }

  @Test
  void should_throw_bad_request_exception_when_dto_is_null() {
    BadRequestException exception =
        assertThrows(BadRequestException.class, () -> PostOrganization.from(null));
    assertEquals("PostOrganization can not be null", exception.getMessage());
  }

  @Test
  void should_throw_bad_request_exception_when_name_is_blank() {
    BadRequestException exception =
        assertThrows(
            BadRequestException.class, () -> PostOrganization.from(new PostOrganization("")));
    assertEquals("Name of organization can not be blank", exception.getMessage());
  }
}
