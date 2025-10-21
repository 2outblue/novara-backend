package com.novaraspace.model.exception;

import com.novaraspace.model.enums.ErrCode;
import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException {
    private final ErrCode errorCode;
    private final HttpStatus status;

    public UserException(ErrCode errorCode, HttpStatus status, String message) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public static UserException notFound() {
        return new UserException(ErrCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND, "User not found.");
    }

    public static UserException disabled() {
        return new UserException(ErrCode.USER_DISABLED, HttpStatus.FORBIDDEN, "User disabled.");
    }

    public static UserException fieldUpdateFailed() {
        return new UserException(ErrCode.USER_UPDATE_FAILED, HttpStatus.BAD_REQUEST, "Update failed.");
    }

    public ErrCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
