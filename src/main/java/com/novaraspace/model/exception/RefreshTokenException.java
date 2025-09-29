package com.novaraspace.model.exception;

public class RefreshTokenException extends RuntimeException{
    public RefreshTokenException() {
        super("Invalid refresh token.");
    }
    public RefreshTokenException(String message) {
        super(message);
    }
}
