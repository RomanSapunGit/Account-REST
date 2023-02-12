package com.myproject.accountrest.exception;


public class UserDataAlreadyExistException extends Exception {
    private static final String DEFAULT_MESSAGE = "User already exist with that data:";
    public UserDataAlreadyExistException(String message) {
        super(message + DEFAULT_MESSAGE);
    }
}
