package com.myproject.accountrest.exception;

public class RoleNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Role not found";
    public RoleNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

}