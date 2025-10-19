package com.novaraspace.model.exception;

import com.novaraspace.model.enums.ErrCode;
import org.springframework.http.HttpStatus;

public class VerificationException extends RuntimeException{
    private final ErrCode errorCode;
    private final HttpStatus status;

    public VerificationException(ErrCode errorCode, HttpStatus status, String message) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public static VerificationException disabled() {
        return new VerificationException(ErrCode.VERIFICATION_DISABLED, HttpStatus.FORBIDDEN, "Verification disabled.");
    }

    public static VerificationException failed() {
        return new VerificationException(ErrCode.VERIFICATION_FAILED, HttpStatus.BAD_REQUEST, "Verification failed.");
    }

    public ErrCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
