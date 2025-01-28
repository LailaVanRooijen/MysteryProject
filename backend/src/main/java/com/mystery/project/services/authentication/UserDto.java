package com.mystery.project.services.authentication;

import java.util.UUID;

public record UserDto(UUID id, String displayName, String email) {}
