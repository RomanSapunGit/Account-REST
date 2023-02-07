package com.example.accountrest.controller;

import com.example.accountrest.exception.*;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.UnsupportedEncodingException;

@RestControllerAdvice
public class HandlerController {
    private static final String MISSED_PARAMETERS_MESSAGE = "One or several parameters was missing";
    @ExceptionHandler(value = {UserNotFoundException.class, TaskNotFoundException.class,
            RoleNotFoundException.class, MessagingException.class, ValuesAreEqualException.class,
            ValuesAreNotEqualException.class, UnsupportedEncodingException.class, TokenExpiredException.class})
    protected ResponseEntity<?> handleConflict(Exception exception ){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

@ExceptionHandler(value = {NullPointerException.class})
    protected ResponseEntity<?> handleNullPointerWithCustomMessage(NullPointerException exception){
        return new ResponseEntity<>(MISSED_PARAMETERS_MESSAGE, HttpStatus.BAD_REQUEST);
}
}
