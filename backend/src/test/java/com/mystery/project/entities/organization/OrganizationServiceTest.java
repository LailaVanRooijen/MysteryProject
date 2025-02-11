package com.mystery.project.entities.organization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mystery.project.entities.organization.dto.GetOrganization;
import com.mystery.project.entities.organization.dto.PostOrganization;
import com.mystery.project.entities.organization.userorganization.UserOrganizationRepository;
import com.mystery.project.entities.user.Role;
import com.mystery.project.entities.user.User;
import com.mystery.project.entities.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class OrganizationServiceTest {
  @InjectMocks private OrganizationService organizationService;

  @Mock private OrganizationRepository organizationRepository;
  @Mock private UserOrganizationRepository userOrganizationRepository;
  @Mock private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void should_successfully_save_organization() {
    PostOrganization postOrganization = new PostOrganization("Test organization");
    User user = new User("human@gmail.com", "Password123!", "human", Role.USER);
    GetOrganization responseOrganization = organizationService.create(postOrganization, user);
    assertEquals(postOrganization.name(), responseOrganization.name());
  }
}
