package com.yuldashov.planner.exceptions;

import com.yuldashov.planner.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<?> usernameExist(UsernameExistException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                409,
                "Такой username уже существует",
                e.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<?> emailExist(EmailExistException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                409,
                "Такой email уже существует",
                e.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<?> taskNotFound(TaskNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                404,
                "Такой задачи не существует",
                e.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
