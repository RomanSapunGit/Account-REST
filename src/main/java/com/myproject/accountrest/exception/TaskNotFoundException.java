package com.myproject.accountrest.exception;

public class TaskNotFoundException extends Exception{
    private static final String DEFAULT_MESSAGE = "Task not found";
    public TaskNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
