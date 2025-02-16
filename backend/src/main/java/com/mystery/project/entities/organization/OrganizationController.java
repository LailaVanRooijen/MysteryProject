package com.mystery.project.entities.organization;

import com.mystery.project.entities.organization.dto.GetOrganization;
import com.mystery.project.entities.organization.dto.PostOrganization;
import com.mystery.project.entities.user.User;
import com.mystery.project.mainconfiguration.Routes;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
      @RequestBody @Valid PostOrganization postOrganization, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    GetOrganization savedOrganisation =
        GetOrganization.to(organizationService.create(postOrganization, user));
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedOrganisation.id())
            .toUri();

    return ResponseEntity.created(location).body(savedOrganisation);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Organization> getById(
      @PathVariable Long id, Authentication authentication) {

    User user = (User) authentication.getPrincipal();
    Organization organization = organizationService.getById(id, user);

    return ResponseEntity.ok(organization);
  }

  @GetMapping
  public Page<GetOrganization> getAll(Pageable pageable) {
    return organizationService.getAll(pageable).map(GetOrganization::to);
  }

  @PostMapping("/{organizationId}/users/add/{studentId}")
  public ResponseEntity<Void> addStudentToOrganization(
      @PathVariable Long organizationId,
      @PathVariable UUID studentId,
      Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    organizationService.addStudentToOrganization(organizationId, loggedInUser, studentId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{organizationId}/users/remove/{studentId}")
  public ResponseEntity<Void> removeStudentFromOrganization(
      @PathVariable Long organizationId,
      @PathVariable UUID studentId,
      Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    organizationService.removeStudentFromOrganization(organizationId, loggedInUser, studentId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    organizationService.deleteOrganization(id, user);
    return ResponseEntity.ok().build();
  }
}
