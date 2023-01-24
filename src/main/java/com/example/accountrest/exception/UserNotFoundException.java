package com.example.accountrest.exception;



public class UserNotFoundException extends Exception {
    private static final String DEFAULT_MESSAGE = "User not found";
    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
