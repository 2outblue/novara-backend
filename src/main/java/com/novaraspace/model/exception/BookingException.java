package com.novaraspace.model.exception;

import com.novaraspace.model.enums.ErrCode;
import org.springframework.http.HttpStatus;

public class BookingException extends RuntimeException {
    private final ErrCode errorCode;
    private final HttpStatus status;

    public BookingException(ErrCode errorCode, HttpStatus status, String message) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public static BookingException invalidQuote() {
        return new BookingException(ErrCode.INVALID_QUOTE, HttpStatus.BAD_REQUEST, "Could not validate booking quote");
    }

    public ErrCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
