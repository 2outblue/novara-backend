package com.novaraspace.model.exception;

import com.novaraspace.model.enums.ErrCode;
import org.springframework.http.HttpStatus;

public class FlightException extends RuntimeException{
    private final ErrCode errorCode;
    private final HttpStatus status;

    public FlightException(ErrCode errorCode, HttpStatus status, String message) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public static FlightException notFound() {
        return new FlightException(ErrCode.FLIGHT_NOT_FOUND, HttpStatus.NOT_FOUND, "Flight not found");
    }

    public static FlightException noAvailability() {
        return new FlightException(ErrCode.NO_FLIGHT_AVAILABILITY, HttpStatus.BAD_REQUEST, "No flight availability");
    }

    public static FlightException reservationFailed() {
        return new FlightException(ErrCode.NO_FLIGHT_AVAILABILITY, HttpStatus.BAD_REQUEST, "Could not reserve flight");
    }

    public ErrCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
