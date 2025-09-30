package com.novaraspace.web;

import com.novaraspace.model.exception.ApiError;
import com.novaraspace.model.exception.ExpiredRefreshTokenException;
import com.novaraspace.model.exception.RefreshTokenException;
import com.novaraspace.model.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler { //TODO: Split this if it gets too big

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<ApiError> handleInvalidToken(RefreshTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError(HttpStatus.UNAUTHORIZED.value(), "INVALID_TOKEN", ex.getMessage()));
    }

    @ExceptionHandler(ExpiredRefreshTokenException.class)
    public ResponseEntity<ApiError> handleExpiredToken(ExpiredRefreshTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError(HttpStatus.UNAUTHORIZED.value(), "EXPIRED_TOKEN", ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(HttpStatus.NOT_FOUND.value(), "USER_NOT_FOUND", ex.getMessage()));
    }

}
