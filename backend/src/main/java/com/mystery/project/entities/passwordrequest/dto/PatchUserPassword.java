package com.mystery.project.entities.passwordrequest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PatchUserPassword(
    @NotNull @NotBlank String password, @NotBlank @NotNull @Email String email) {}
