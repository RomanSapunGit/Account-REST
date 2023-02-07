package com.example.accountrest.exception;

public class AuthProhibitedException  extends Exception{
    private static final String DEFAULT_MESSAGE = "Authentication is prohibited";
    public AuthProhibitedException() {
        super(DEFAULT_MESSAGE);
    }

}
