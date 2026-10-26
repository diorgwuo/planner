package com.yuldashov.planner.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Username не может быть пустым")
        String username,
        @NotBlank(message = "Password не может быть пустым")
        String password) {}

