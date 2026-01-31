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

    public static BookingException notFound() {
        return new BookingException(ErrCode.NOT_FOUND, HttpStatus.BAD_REQUEST, "Booking not found.");
    }

    public static BookingException invalidQuote() {
        return new BookingException(ErrCode.INVALID_QUOTE, HttpStatus.BAD_REQUEST, "Invalid booking quote");
    }

    public static BookingException creationFailed() {
        return new BookingException(ErrCode.BOOKING_CREATION_FAILED, HttpStatus.BAD_REQUEST, "Invalid booking request data");
    }

    public static BookingException creationFailed(String message) {
        return new BookingException(ErrCode.BOOKING_CREATION_FAILED, HttpStatus.BAD_REQUEST, message);
    }

    public ErrCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
