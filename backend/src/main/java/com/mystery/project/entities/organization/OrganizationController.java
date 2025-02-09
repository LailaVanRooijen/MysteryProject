package com.mystery.project.entities.organization;

import com.mystery.project.entities.organization.dto.GetOrganization;
import com.mystery.project.entities.organization.dto.PostOrganization;
import com.mystery.project.entities.user.User;
import com.mystery.project.mainconfiguration.Routes;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(Routes.ORGANIZATIONS)
@CrossOrigin(origins = "${spring.frontend-client}")
@RequiredArgsConstructor
public class OrganizationController {
  public final OrganizationService organizationService;

  @PostMapping
  public ResponseEntity<GetOrganization> create(
      @RequestBody PostOrganization postOrganization, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    GetOrganization savedOrganisation = organizationService.create(postOrganization, user);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedOrganisation.id())
            .toUri();
    return ResponseEntity.created(location).body(savedOrganisation);
  }

  @PostMapping("/{organizationId}/users/add/{studentId}")
  public ResponseEntity<Organization> addStudentToOrganization(
      @PathVariable Long organizationId,
      @PathVariable UUID studentId,
      Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    organizationService.addStudentToOrganization(organizationId, loggedInUser, studentId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Organization> delete(@PathVariable Long id, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    organizationService.deleteOrganization(id, user);
    return ResponseEntity.ok().build();
  }
}
