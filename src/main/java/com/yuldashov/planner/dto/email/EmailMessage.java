package com.yuldashov.planner.dto.email;

public record EmailMessage (
    String address,
    String title,
    String body) {}
