package com.novaraspace.model.exception;

public class FailedOperationException extends RuntimeException{
    public FailedOperationException() {
        super("Failed operation.");
    }

    public FailedOperationException(String message) {
        super(message);
    }
}
