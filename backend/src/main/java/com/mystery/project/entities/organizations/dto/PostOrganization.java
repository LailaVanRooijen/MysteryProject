package com.mystery.project.entities.organizations.dto;

import com.mystery.project.entities.organizations.Organization;

public record PostOrganization(String name) {
  public static Organization from(PostOrganization dto) {
    return new Organization(dto.name);
  }
}
