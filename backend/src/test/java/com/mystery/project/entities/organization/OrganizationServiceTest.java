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
  // TODO morgen zoek deze uit :
  // https://stackoverflow.com/questions/79278490/mockito-is-currently-self-attaching-to-enable-the-inline-mock-maker-this-will-n
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
    // given
    PostOrganization postOrganization = new PostOrganization("Test organization");
    User user = new User("human@gmail.com", "Password123!", "human", Role.USER);
    // when
    GetOrganization responseOrganization = organizationService.create(postOrganization, user);
    // then
    assertEquals(postOrganization.name(), responseOrganization.name());
  }
}
