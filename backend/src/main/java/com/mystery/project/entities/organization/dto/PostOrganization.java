package com.mystery.project.entities.organization.dto;

import com.mystery.project.entities.organization.Organization;
import com.mystery.project.exception.BadRequestException;

public record PostOrganization(String name) {
  public static Organization from(PostOrganization dto) {
    if (dto == null) {
      throw new BadRequestException("PostOrganization can not be null");
    }

    if (dto.name.isBlank()) {
      throw new BadRequestException("Name of organization can not be blank");
    }

    return new Organization(dto.name);
  }
}
