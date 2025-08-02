package com.github.kzhunmax.footballstatsplatform.exception;

import com.github.kzhunmax.footballstatsplatform.payload.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String REQUEST_ID_MDC_KEY = "requestId";

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleUsernameExistsException(UsernameExistsException ex) {
        String requestId = MDC.get(REQUEST_ID_MDC_KEY);
        log.warn("UsernameExistsException caught: {} | requestId={}", ex.getMessage(), requestId);
        return ApiResponse.error(HttpStatus.BAD_REQUEST, "USERNAME_TAKEN", ex.getMessage(), requestId);
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleEmailExistsException(EmailExistsException ex) {
        String requestId = MDC.get(REQUEST_ID_MDC_KEY);
        log.warn("EmailExistsException caught: {} | requestId={}", ex.getMessage(), requestId);
        return ApiResponse.error(HttpStatus.BAD_REQUEST, "EMAIL_TAKEN", ex.getMessage(), requestId);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        String requestId = MDC.get(REQUEST_ID_MDC_KEY);
        log.warn("IllegalArgumentException caught: {} | requestId={}", ex.getMessage(), requestId);
        return ApiResponse.error(HttpStatus.BAD_REQUEST, "INVALID_DATA", ex.getMessage(), requestId);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthenticationException(AuthenticationException ex) {
        String requestId = MDC.get(REQUEST_ID_MDC_KEY);
        log.warn("AuthenticationException caught: {} | requestId={}", ex.getMessage(), requestId);
        return ApiResponse.error(
                HttpStatus.UNAUTHORIZED,
                "AUTH_FAILED",
                "Invalid username or password",
                requestId
        );

    }
}
