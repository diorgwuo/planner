package com.yuldashov.planner.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Username не может быть пустым")
        @Size(min = 3, max = 20, message = "Username от 3 до 20 символов")
        String username,
        @NotBlank(message = "Password не может быть пустым")
        @Size(min = 4, message = "Password от 4 символов")
        String password,
        @NotBlank(message = "Email не может быть пустым")
        @Email String email) {}

