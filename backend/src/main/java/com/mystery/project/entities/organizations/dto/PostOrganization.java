package com.mystery.project.entities.organizations.dto;

import com.mystery.project.entities.organizations.Organization;
import jakarta.validation.constraints.NotBlank;

public record PostOrganization(@NotBlank String name) {
  public static Organization from(PostOrganization dto) {
    return new Organization(dto.name);
  }
}
