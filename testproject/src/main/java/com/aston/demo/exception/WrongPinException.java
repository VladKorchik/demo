package com.aston.demo.exception;

public class WrongPinException extends RuntimeException{
    public WrongPinException(String text) {
        super(text);
    }
}
