package com.tracker.smarttracker.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ControllerExceptionHandlers {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ValidationError methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var result = ex.getBindingResult();
        var fieldErrors = result.getFieldErrors();

        var error = new ValidationError(HttpStatus.BAD_REQUEST.value(), "validation error");
        for (org.springframework.validation.FieldError fieldError : fieldErrors) {
            error.addFieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }
        return error;
    }

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex) {
        return getResponseEntityByException(ex, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(ArgumentNotValidException.class)
    protected ResponseEntity<?> handleArgumentNotValidException(ArgumentNotValidException ex) {
        return getResponseEntityByException(ex, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> getResponseEntityByException(Exception ex, HttpStatus status) {
        ApiError apiError = new ApiError(status, ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @Getter
    class ValidationError {
        private final int status;
        private final String message;
        private final List<FieldError> fieldErrors = new ArrayList<>();

        ValidationError(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public void addFieldError(String objectName, String path, String message) {
            var error = new FieldError(objectName, path, message);
            fieldErrors.add(error);
        }
    }

    @Getter
    class ApiError {
        private HttpStatus status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        private LocalDateTime timestamp;
        private String message;

        ApiError(HttpStatus status, String message) {
            this.timestamp = LocalDateTime.now();
            this.status = status;
            this.message = message;
        }
    }
}
