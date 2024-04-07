package com.example.timetracking.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

/**
 * Handles exceptions thrown by controllers.
 * - Logs ResponseStatusException with info level.
 * - Logs unexpected exceptions with error level.
 * - Returns ApiError objects with request details and error info.
 */
@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleException(ResponseStatusException e,
                                                    HttpServletRequest request) {
        log.info("Handling ResponseStatusException: {}", e.getMessage());
        ApiError apiError = buildError(request.getRequestURI(), e.getMessage(), e.getStatusCode().value());
        log.info("Created ApiError for ResponseStatusException: {}", apiError);
        return ResponseEntity.status(apiError.statusCode()).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e,
                                                    HttpServletRequest request) {
        log.error("Got unexpected exception: ", e);
        ApiError apiError = buildError(request.getRequestURI(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        log.info("Created ApiError for unexpected exception: {}", apiError);
        return ResponseEntity.status(apiError.statusCode()).body(apiError);
    }

    private ApiError buildError(String uri, String message, int code) {
        ApiError apiError = new ApiError(uri, message, code);
        log.info("Created api error: {}", apiError);
        return apiError;
    }
}
