package com.processor.orderprocessing.application.exception;

public class DuplicateRequestException extends RuntimeException {

    public DuplicateRequestException(String requestId) {
        super("Duplicate request detected: " + requestId);
    }
}
