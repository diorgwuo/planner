package com.yuldashov.planner.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Username не может быть пустым")
    @Size(min = 3, max = 20, message = "Username от 3 до 20 символов")
    private String username;
    @NotBlank(message = "Password не может быть пустым")
    @Size(min = 4, message = "Password от 4 символов")
    private String password;
    @NotBlank(message = "Email не может быть пустым")
    @Email
    private String email;

    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public RegisterRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

