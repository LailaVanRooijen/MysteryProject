package com.mystery.project.entities.organization.dto;

import com.mystery.project.entities.organization.Organization;
import com.mystery.project.exception.BadRequestException;

public record GetOrganization(Long id, String name) {
  public static GetOrganization to(Organization entity) {
    if (entity == null) {
      throw new BadRequestException("Organization is null");
    }
    return new GetOrganization(entity.getId(), entity.getName());
  }
}
