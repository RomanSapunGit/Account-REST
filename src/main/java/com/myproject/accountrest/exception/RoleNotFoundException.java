package com.myproject.accountrest.exception;

public class RoleNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Role not found. Check if that role matches with these roles: ROLE_USER," +
            " ROLE_ADMIN or ROLE_DISABLE";
    public RoleNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

}
