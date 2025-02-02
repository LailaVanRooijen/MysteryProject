package com.mystery.project.entities.organization.dto;

import com.mystery.project.entities.organization.Organization;

public record PostOrganization(String name) {
  public static Organization from(PostOrganization dto) {
    return new Organization(dto.name);
  }
}
