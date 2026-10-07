package com.yuldashov.planner.controllers;

import com.yuldashov.planner.dto.RegisterRequest;
import com.yuldashov.planner.models.User;
import com.yuldashov.planner.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest registerRequest
    ) {

        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String email = registerRequest.getEmail();

        String token = userService.register(username, password, email);


        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findUserByUsername(username);
        Long userId = user.getId();
        String userEmail = user.getEmail();
        return ResponseEntity.ok(Map.of(
                "id", userId,
                "email", userEmail
        ));
    }
}
