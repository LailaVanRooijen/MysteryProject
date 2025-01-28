package com.mystery.project.controllers;

import com.mystery.project.mainconfiguration.Routes;
import com.mystery.project.services.authentication.AuthenticationService;
import com.mystery.project.services.authentication.LoginRequestDto;
import com.mystery.project.services.authentication.RegisterRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.AUTHENTICATION)
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService authenticationService;
  private final AuthenticationManager authenticationManager;
  private final SecurityContextRepository securityContextRepository;

  @PostMapping("/login")
  public void login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request, HttpServletResponse response) {
    var authenticationResult = authenticationService.loginUser(loginRequestDto);

    var context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authenticationResult);
    SecurityContextHolder.setContext(context);
    securityContextRepository.saveContext(context, request, response);
  }

  @PostMapping("/register")
  public void register(@RequestBody RegisterRequestDto registerRequestDto, HttpServletRequest request, HttpServletResponse response) {
    var authenticationResult = authenticationService.registerUser(registerRequestDto);
    var context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authenticationResult);
    SecurityContextHolder.setContext(context);
    securityContextRepository.saveContext(context, request, response);
  }
}
