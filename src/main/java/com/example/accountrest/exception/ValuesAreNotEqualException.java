package com.example.accountrest.exception;

public class ValuesAreNotEqualException extends Exception{
    private static final String DEFAULT_MESSAGE = "Values are not equal";
    public ValuesAreNotEqualException() {
        super(DEFAULT_MESSAGE);
    }

}
