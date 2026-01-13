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
        return new BookingException(ErrCode.INVALID_QUOTE, HttpStatus.BAD_REQUEST, "Invalid booking quote");
    }

    public static BookingException invalidCreationData() {
        return new BookingException(ErrCode.INVALID_BOOKING_REQUEST, HttpStatus.BAD_REQUEST, "Invalid booking request data");
    }

    public static BookingException invalidCreationData(String message) {
        return new BookingException(ErrCode.INVALID_BOOKING_REQUEST, HttpStatus.BAD_REQUEST, message);
    }

    public ErrCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
