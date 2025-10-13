package com.novaraspace.model.exception;

public class FailedSendingEmailException extends RuntimeException {
    public FailedSendingEmailException() {
        super("Failed sending email.");
    }

    public FailedSendingEmailException(String message) {
        super(message);
    }
}
