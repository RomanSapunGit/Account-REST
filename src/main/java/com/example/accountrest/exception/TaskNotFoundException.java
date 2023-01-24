package com.example.accountrest.exception;

public class TaskNotFoundException extends Exception{
    private static final String DEFAULT_MESSAGE = "Role not found";
    public TaskNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
