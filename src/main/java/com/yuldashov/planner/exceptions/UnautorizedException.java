package com.yuldashov.planner.exceptions;

public class UnautorizedException extends RuntimeException {
    public UnautorizedException() {
        super("Вы не авторизованы");
    }
}
