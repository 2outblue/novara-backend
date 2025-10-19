package com.novaraspace.model.exception;

import com.novaraspace.model.enums.ErrCode;
import org.springframework.http.HttpStatus;

public class RefreshTokenException extends RuntimeException{
    private final ErrCode errorCode;
    private final HttpStatus status;

    public RefreshTokenException(ErrCode errorCode, HttpStatus status, String message) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public static RefreshTokenException invalid() {
        return new RefreshTokenException(ErrCode.INVALID_TOKEN, HttpStatus.UNAUTHORIZED, "Invalid refresh token.");
    }

    public static RefreshTokenException expired() {
        return new RefreshTokenException(ErrCode.EXPIRED_TOKEN, HttpStatus.UNAUTHORIZED, "Expired refresh token.");
    }

    public ErrCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
