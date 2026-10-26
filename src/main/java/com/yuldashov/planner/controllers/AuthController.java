package com.yuldashov.planner.controllers;

import com.yuldashov.planner.dto.auth.LoginRequest;
import com.yuldashov.planner.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(
            @Valid
            @RequestBody LoginRequest request,
            HttpServletResponse response) {
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authService.login(request));
    }
}
