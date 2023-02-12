package com.myproject.accountrest.exception;

public class ValuesAreEqualException extends Exception {
    private static final String DEFAULT_MESSAGE = "Values are equal";
    public ValuesAreEqualException() {
        super(DEFAULT_MESSAGE);
    }

}
