package com.example.accountrest.exception;

public class RoleNotFoundException extends Exception{
    private static final String DEFAULT_MESSAGE = "Role not found";
    public RoleNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

}
