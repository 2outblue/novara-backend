package com.novaraspace.model.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ApiError {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> fieldErrors;

    public ApiError() {
    }

    public ApiError(int status, String error, String message) {
        this.timestamp = Instant.now().toString();
        this.status = status;
        this.error = error;
        this.message = message;
        this.fieldErrors = new HashMap<>();
    }

    public ApiError(int status, String error, String message, Map<String, String> fieldErrors) {
        this.timestamp = Instant.now().toString();
        this.status = status;
        this.error = error;
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

    public String getError() {
        return error;
    }

    public ApiError setError(String error) {
        this.error = error;
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
