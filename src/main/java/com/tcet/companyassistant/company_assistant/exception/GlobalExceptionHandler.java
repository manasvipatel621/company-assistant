package com.tcet.companyassistant.company_assistant.exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(
            ResourceNotFoundException exception) {

        logger.warn("Resource not found: {}", exception.getMessage());
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Not Found",
                exception.getMessage(),
                null
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        logger.warn("Request validation failed for {} field(s)", errors.size());
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                "One or more request fields are invalid",
                errors
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(
            ConstraintViolationException exception) {

        Map<String, String> errors = new LinkedHashMap<>();
        exception.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );

        logger.warn("Constraint validation failed for {} field(s)", errors.size());
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                "One or more request constraints are invalid",
                errors
        );
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<Map<String, Object>> handleBadRequest(Exception exception) {

        logger.warn("Malformed or unsupported request: {}", exception.getMessage());
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                "The request could not be processed",
                null
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException exception) {

        logger.warn("HTTP method not supported: {}", exception.getMessage());
        return buildResponse(
                HttpStatus.METHOD_NOT_ALLOWED,
                "Method Not Allowed",
                "The HTTP method is not supported for this endpoint",
                null
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(
            DataIntegrityViolationException exception) {

        logger.warn("Data integrity violation", exception);
        return buildResponse(
                HttpStatus.CONFLICT,
                "Conflict",
                "The request conflicts with existing data",
                null
        );
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, Object>> handleDataAccessException(
            DataAccessException exception) {

        logger.error("Database operation failed", exception);
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Database Error",
                "The database operation could not be completed",
                null
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(
            ResponseStatusException exception) {

        HttpStatus status = HttpStatus.resolve(exception.getStatusCode().value());
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return buildResponse(
                status,
                status.getReasonPhrase(),
                exception.getReason() == null ? status.getReasonPhrase() : exception.getReason(),
                null
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(
            Exception exception) {

        logger.error("Unhandled application exception", exception);
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected error occurred",
                null
        );
    }

    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status,
            String error,
            String message,
            Map<String, String> fieldErrors) {

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", message);

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            response.put("errors", fieldErrors);
        }

        return ResponseEntity.status(status).body(response);
    }
}
