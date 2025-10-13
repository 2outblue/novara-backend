package com.novaraspace.model.exception;

public class DisabledVerificationTokenException extends VerificationTokenException{
    public DisabledVerificationTokenException() {
        super("Verification code is disabled.");
    }

    public DisabledVerificationTokenException(String message) {
        super(message);
    }
}
