package br.com.karatedopi.services.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest httpServletRequest) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setError("Resource not found");
        standardError.setMessage(ex.getMessage());
        standardError.setPath(httpServletRequest.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest httpServletRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setTimestamp(Instant.now());
        validationError.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        validationError.setError("Validation Error");
        validationError.setMessage(ex.getMessage());
        validationError.setPath(httpServletRequest.getRequestURI());
        for (FieldError fieldError: ex.getBindingResult().getFieldErrors()) {
            validationError.addCustomFieldError(new CustomFieldError(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value()).body(validationError);
    }

    @ExceptionHandler(InvalidAuthenticationException.class)
    public ResponseEntity<ValidationError> invalidAuthenticationException(InvalidAuthenticationException ex, HttpServletRequest httpServletRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setTimestamp(Instant.now());
        validationError.setStatus(HttpStatus.BAD_REQUEST.value());
        validationError.setError("Authentication Error");
        validationError.setMessage(ex.getMessage());
        validationError.setPath(httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(validationError);
    }
}
