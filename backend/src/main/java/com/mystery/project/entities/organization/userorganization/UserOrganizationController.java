package com.mystery.project.entities.organization.userorganization;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO delete me, temporary controller for debugging
@RequiredArgsConstructor
@RestController
@RequestMapping("/test/user-orgs")
public class UserOrganizationController {
  private final UserOrganizationRepository userOrganizationRepository;

  @GetMapping()
  public List<MemberDto> getAll() {
    return userOrganizationRepository.findAll().stream().map(MemberDto::to).toList();
  }
}
