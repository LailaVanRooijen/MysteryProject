package com.mystery.project.services.authentication;

import com.mystery.project.entities.user.Role;
import com.mystery.project.entities.user.User;
import com.mystery.project.exception.DuplicateDisplayNameException;
import com.mystery.project.exception.DuplicateEmailException;
import com.mystery.project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final SecurityContextRepository securityContextRepository;

  public Authentication registerUser(RegisterRequestDto registerRequestDto) {
    log.trace("Registering user...");
    var nameCheck = userRepository.findByDisplayName(registerRequestDto.displayName());
    if (nameCheck != null) {
      throw new DuplicateDisplayNameException("Display name is taken.");
    }

    var emailCheck = userRepository.findByEmail(registerRequestDto.email());
    if (nameCheck != null) {
      throw new DuplicateEmailException("Email is already registered.");
    }

    log.trace("Checks passed, saving user to repo...");

    var user =
        userRepository.save(
            new User(
                UUID.randomUUID(),
                registerRequestDto.email(),
                passwordEncoder.encode(registerRequestDto.password()),
                registerRequestDto.displayName(),
                Role.USER));

    log.trace("User saved, authenticating...");

    var authenticationToken =
        new UsernamePasswordAuthenticationToken(registerRequestDto.email(), registerRequestDto.password());
    return authenticationManager.authenticate(authenticationToken);
  }

  public Authentication loginUser(LoginRequestDto loginRequestDto) {
    var authenticationRequest =
        new UsernamePasswordAuthenticationToken(
            loginRequestDto.email(), loginRequestDto.password());
    return authenticationManager.authenticate(authenticationRequest);
  }
}
