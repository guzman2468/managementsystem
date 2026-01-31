package com.library.managementsystem.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NameEmailRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Email is required")
        String email
) {}
