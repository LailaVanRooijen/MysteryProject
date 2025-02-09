package com.mystery.project.entities.organizations;

import com.mystery.project.entities.organizations.dto.GetOrganization;
import com.mystery.project.entities.organizations.dto.PostOrganization;
import com.mystery.project.entities.user.User;
import com.mystery.project.mainconfiguration.Routes;
import java.net.URI;

import jakarta.validation.Valid;
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
      @RequestBody @Valid PostOrganization postOrganization, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    GetOrganization savedOrganisation = organizationService.create(postOrganization, user);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedOrganisation.id())
            .toUri();
    return ResponseEntity.created(location).body(savedOrganisation);
  }
}
