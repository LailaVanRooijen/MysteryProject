package com.mystery.project.authentication;

import com.mystery.project.authentication.dtos.LoginRequestDto;
import com.mystery.project.authentication.dtos.RegisterRequestDto;
import com.mystery.project.mainconfiguration.Routes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.AUTHENTICATION)
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService authenticationService;
  private final SecurityContextRepository securityContextRepository;

  @PostMapping("/login")
  public void login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request, HttpServletResponse response) {
    Authentication authenticationResult = authenticationService.loginUser(loginRequestDto);
    SecurityContext context = SecurityContextHolder.createEmptyContext();

    context.setAuthentication(authenticationResult);
    SecurityContextHolder.setContext(context);
    securityContextRepository.saveContext(context, request, response);
  }

  @PostMapping("/register")
  public void register(@RequestBody RegisterRequestDto registerRequestDto, HttpServletRequest request, HttpServletResponse response) {
    Authentication authenticationResult = authenticationService.registerUser(registerRequestDto);
    SecurityContext context = SecurityContextHolder.createEmptyContext();

    context.setAuthentication(authenticationResult);
    SecurityContextHolder.setContext(context);
    securityContextRepository.saveContext(context, request, response);
  }

  @GetMapping("/me")
  public String me(@AuthenticationPrincipal UserDetails userDetails) {
    if (userDetails == null) {
      return "No current user.";
    }

    return userDetails.getUsername();
  }
}
