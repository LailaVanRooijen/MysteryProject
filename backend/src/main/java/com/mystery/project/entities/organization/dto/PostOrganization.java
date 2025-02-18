package com.mystery.project.entities.organization.dto;

import com.mystery.project.entities.organization.Organization;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostOrganization(@NotNull @NotBlank String name) {
  public static Organization from(PostOrganization dto) {
    return new Organization(dto.name);
  }
}
