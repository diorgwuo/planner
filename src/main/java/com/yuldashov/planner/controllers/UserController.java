package com.yuldashov.planner.controllers;

import com.yuldashov.planner.dto.auth.RegisterRequest;
import com.yuldashov.planner.dto.auth.UserInfoResponse;
import com.yuldashov.planner.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public void register(
            @Valid @RequestBody RegisterRequest registerRequest,
            HttpServletResponse response
    ) {
        String token = userService.register(registerRequest.username(),
                registerRequest.password(),
                registerRequest.email());
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public UserInfoResponse getUser(Authentication auth) {
        return userService.findUserByUsername(auth);
    }
}
