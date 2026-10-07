package com.yuldashov.planner.exceptions;

public class UsernameExistException extends RuntimeException{
    public UsernameExistException(String message) {
        super(message);
    }
}
