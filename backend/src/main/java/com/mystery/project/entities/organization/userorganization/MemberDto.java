package com.mystery.project.entities.organization.userorganization;

import java.util.UUID;

// TODO delete me Temporary for debugging
public record MemberDto(UUID userId, String username, String organization, OrganizationRole role) {
  public static MemberDto to(UserOrganization entity) {
    return new MemberDto(
        entity.getUser().getId(),
        entity.getUser().getDisplayName(),
        entity.getOrganization().getName(),
        entity.getOrganizationRole());
  }
}
