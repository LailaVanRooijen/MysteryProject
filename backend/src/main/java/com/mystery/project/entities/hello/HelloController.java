package com.mystery.project.entities.hello;

import com.mystery.project.mainconfiguration.Routes;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

// Temporary hello endpoint as per request

@RestController
@RequestMapping(Routes.BASE_ROUTE)
public class HelloController {
  @GetMapping
  public String sayHello(Principal principal) {
    return "Authenticated as " + principal.getName();
  }
}
