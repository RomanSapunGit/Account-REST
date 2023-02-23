package com.myproject.accountrest.controller;

import com.myproject.accountrest.dto.ResponseExceptionDTO;
import com.myproject.accountrest.exception.*;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.EmptyStackException;


@RestControllerAdvice
public class HandlerController {
    private static final String MISSED_PARAMETERS_MESSAGE = "One or several parameters was missing";
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {UserNotFoundException.class, TaskNotFoundException.class,
            MessagingException.class, ValuesAreEqualException.class, ValuesAreNotEqualException.class,
            UnsupportedEncodingException.class, UserDataAlreadyExistException.class})
    protected ResponseExceptionDTO handleConflict(Exception exception) {
        return ResponseExceptionDTO.builder()
                .exception(exception.getClass().getName())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(Timestamp.from(ZonedDateTime.now().toInstant()))
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(value = {NullPointerException.class})
    protected ErrorResponse handleNullPointerWithCustomMessage() {
        return ErrorResponse.builder(new Throwable(), HttpStatus.INTERNAL_SERVER_ERROR, MISSED_PARAMETERS_MESSAGE).build();
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {RoleNotFoundException.class, EmptyStackException.class, TokenExpiredException.class})
    protected ResponseExceptionDTO handleRuntimeConflict(RuntimeException exception) {
        return ResponseExceptionDTO.builder()
                .exception(exception.toString())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(Timestamp.from(ZonedDateTime.now().toInstant()))
                .build();
    }
}
