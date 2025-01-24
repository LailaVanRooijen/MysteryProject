package com.mystery.project.entities.hello;

import com.mystery.project.mainconfiguration.Routes;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

// Temporary hello endpoint as per request

@RestController
@RequestMapping(Routes.BASE_ROUTE)
public class HelloController {
  @GetMapping
  public String sayHello(@AuthenticationPrincipal OAuth2User principal) {
    if (principal != null) {
      return "Hello " + principal.getAttribute("name");
    }
    return "Not authenticated";
  }
}
