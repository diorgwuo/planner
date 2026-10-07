package com.yuldashov.planner.dto;

public class JwtRequest {

    private String login;
    private String password;
    private String email;

    public JwtRequest(String username, String password, String email) {
        this.login = username;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
