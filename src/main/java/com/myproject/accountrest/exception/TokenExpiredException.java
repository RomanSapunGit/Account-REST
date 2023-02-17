package com.myproject.accountrest.exception;

public class TokenExpiredException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Token expired";
    public TokenExpiredException() {
        super(DEFAULT_MESSAGE);
    }

}
