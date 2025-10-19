package com.novaraspace.model.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ApiError {
    private String timestamp;
    private int status;
    private String errorCode;
    private String message;
    private Map<String, String> fieldErrors;

    public ApiError() {
    }

    public ApiError(int status, String errorCode, String message) {
        this.timestamp = Instant.now().toString();
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.fieldErrors = new HashMap<>();
    }

    public ApiError(int status, String errorCode, String message, Map<String, String> fieldErrors) {
        this.timestamp = Instant.now().toString();
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public ApiError setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public ApiError setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public ApiError setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiError setMessage(String message) {
        this.message = message;
        return this;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public ApiError setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
        return this;
    }
}
