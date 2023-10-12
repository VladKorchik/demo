package com.aston.demo.exception;

public class NotEnoughMoney extends RuntimeException{
    public NotEnoughMoney(String s) {
        super(s);
    }

}
