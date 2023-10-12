package com.aston.demo.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String text) {
        super(text);
    }
}
