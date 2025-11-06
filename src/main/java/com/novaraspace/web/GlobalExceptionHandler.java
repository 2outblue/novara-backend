package com.novaraspace.web;


import com.novaraspace.model.enums.ErrCode;
import com.novaraspace.model.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<ApiError> handleInvalidToken(RefreshTokenException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ApiError(ex.getStatus().value(), ex.getErrorCode().toString(), ex.getMessage()));
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body(new ApiError(HttpStatus.UNAUTHORIZED.value(), "INVALID_TOKEN", ex.getMessage()));
    }

//    @ExceptionHandler(ExpiredRefreshTokenException.class)
//    public ResponseEntity<ApiError> handleExpiredToken(ExpiredRefreshTokenException ex) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body(new ApiError(HttpStatus.UNAUTHORIZED.value(), "EXPIRED_TOKEN", ex.getMessage()));
//    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiError> handleUserException(UserException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ApiError(ex.getStatus().value(), ex.getErrorCode().toString(), ex.getMessage()));
    }

//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(new ApiError(HttpStatus.NOT_FOUND.value(), "USER_NOT_FOUND", ex.getMessage()));
//    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationExceptions(AuthenticationException ex) {
        String message = "Bad Credentials.";
        String errorCode = "BAD_CREDENTIALS";
        if (ex instanceof BadCredentialsException || ex instanceof UsernameNotFoundException || ex instanceof InternalAuthenticationServiceException) {
            message = "Invalid username or password.";
        }
        if (ex instanceof DisabledException) {
            message = "Account is not activated";
            errorCode = "ACCOUNT_NOT_ACTIVE";
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(HttpStatus.BAD_REQUEST.value(), ErrCode.BAD_CREDENTIALS.toString(), message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage()));

        String errorCode = "INVALID_DATA";
        if (fieldErrors.get("email").equals("Email already exists.")) {
            errorCode = "EMAIL_IN_USE";
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(HttpStatus.BAD_REQUEST.value(), errorCode, "Validation failed.", fieldErrors));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(HttpStatus.BAD_REQUEST.value(), "INVALID_DATA", "NO_ERROR_MESSAGE"));
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<ApiError> handleEmailException(EmailException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ApiError(ex.getStatus().value(), ex.getErrorCode().toString(), ex.getMessage()));
    }

    @ExceptionHandler(FailedOperationException.class)
    public ResponseEntity<ApiError> handleFailedOperation(FailedOperationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "FAILED_OPERATION", ex.getMessage()));
    }

    @ExceptionHandler(VerificationException.class)
    public ResponseEntity<ApiError> handleVerificationException(VerificationException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ApiError(ex.getStatus().value(), ex.getErrorCode().toString(), ex.getMessage()));
    }

    @ExceptionHandler(FlightException.class)
    public ResponseEntity<ApiError> handleFlightException(FlightException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ApiError(ex.getStatus().value(), ex.getErrorCode().toString(), ex.getMessage()));
    }

//    @ExceptionHandler(VerificationTokenException.class)
//    public ResponseEntity<ApiError> handleVerificationTokenException(VerificationTokenException ex) {
//        String errorCode = "VERIFICATION_FAILED";
//        if (ex instanceof DisabledVerificationTokenException) {
//            errorCode = "VERIFICATION_DISABLED";
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(new ApiError(HttpStatus.BAD_REQUEST.value(), errorCode, ex.getMessage()));
//    }

}
