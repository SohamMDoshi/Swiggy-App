package com.swiggy.order_service.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
