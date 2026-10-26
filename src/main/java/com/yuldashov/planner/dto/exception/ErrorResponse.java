package com.yuldashov.planner.dto.exception;

public record ErrorResponse(int status, String error, String message) {
}
