package com.novaraspace.model.exception;

import com.novaraspace.model.enums.ErrCode;
import org.springframework.http.HttpStatus;

public class EmailException extends RuntimeException{
    private final ErrCode errorCode;
    private final HttpStatus status;

    public EmailException(ErrCode errorCode, HttpStatus status, String message) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public static EmailException sendingFailed() {
        return new EmailException(ErrCode.EMAIL_SENDING_FAILED, HttpStatus.INTERNAL_SERVER_ERROR, "Email sending failed.");
    }

    public ErrCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
