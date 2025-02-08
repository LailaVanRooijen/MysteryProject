package com.mystery.project.entities.organizations.dto;

import com.mystery.project.entities.organizations.Organization;

public record GetOrganization(Long id, String name) {
 
  public static GetOrganization to(Organization entity) {
    return new GetOrganization(entity.getId(), entity.getName());
  }
}
