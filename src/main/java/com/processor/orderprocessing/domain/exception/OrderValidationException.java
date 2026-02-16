package com.processor.orderprocessing.domain.exception;

public class OrderValidationException extends RuntimeException{

    public OrderValidationException(String message){
        super(message);
    }
}
