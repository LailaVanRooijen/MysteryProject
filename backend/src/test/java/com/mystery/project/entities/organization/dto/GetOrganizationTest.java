package com.mystery.project.entities.organization.dto;

import static org.junit.jupiter.api.Assertions.*;

import com.mystery.project.entities.organization.Organization;
import com.mystery.project.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GetOrganizationTest {
  private Organization organization = null;

  @BeforeEach
  void setUp() {
    organization = new Organization("Test");
  }

  @Test
  void should_create_dto_from_entity() {
    GetOrganization dto = GetOrganization.to(organization);
    assertEquals(organization.getName(), dto.name());
    assertEquals(organization.getId(), dto.id());
  }

  @Test
  void should_throw_bad_request_exception_when_organization_is_null() {
    BadRequestException exception =
        assertThrows(BadRequestException.class, () -> GetOrganization.to(null));
    assertEquals("Can not map, organization is null", exception.getMessage());
  }
}
