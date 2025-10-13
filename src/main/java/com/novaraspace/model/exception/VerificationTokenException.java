package com.novaraspace.model.exception;

public class VerificationTokenException extends RuntimeException {
    public VerificationTokenException() {
        super("Error related to account verification.");
    }

    public VerificationTokenException(String message) {
        super(message);
    }
}
