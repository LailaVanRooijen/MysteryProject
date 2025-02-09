package com.mystery.project.entities.organization.dto;

import com.mystery.project.entities.organization.Organization;

public record GetOrganization(Long id, String name) {
  public static GetOrganization to(Organization entity) {
    return new GetOrganization(entity.getId(), entity.getName());
  }
}
