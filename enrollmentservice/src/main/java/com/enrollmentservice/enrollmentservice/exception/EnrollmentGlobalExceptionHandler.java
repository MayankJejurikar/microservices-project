package com.enrollmentservice.enrollmentservice.exception;

import com.enrollmentservice.enrollmentservice.entity.ErrorResponse;
import com.enrollmentservice.enrollmentservice.entity.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class EnrollmentGlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handlingNotFoundException(NotFoundException exception, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI(),
                null
        );

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyEnrolledException.class)
    public ResponseEntity<ErrorResponse> handlingAlreadyEnrolledException(AlreadyEnrolledException exception, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI(),
                null
        );

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handlingServiceUnavailableException(ServiceUnavailableException exception, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "SERVICE_UNAVAILABLE",
                exception.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {

        Map<String, String> validationError = new HashMap<>();

        exception
                .getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    FieldError fieldError = (FieldError) error;
                    String fieldName = fieldError.getField();
                    String message = fieldError.getDefaultMessage();

                    validationError.put(fieldName, message);
                });

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                "Invalid Input",
                request.getRequestURI(),
                validationError
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handlingConstraintViolationException(ConstraintViolationException exception, HttpServletRequest request) {

        Map<String, String> validationError = new HashMap<>();

        exception.
                getConstraintViolations().
                forEach((voilation) -> {
                    String fieldName = voilation.getPropertyPath().toString();
                    String message = voilation.getMessage();

                    validationError.put(fieldName, message);
                });

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                "Invalid path variable or request parameter",
                request.getRequestURI(),
                validationError
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


   @ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handlingGlobalException(Exception exception, HttpServletRequest request)
			throws Exception {

	   if(request.getRequestURI().startsWith("/actuator"))
		   throw exception;
	   
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Something went wrong! . Please try again later.",
                request.getRequestURI(),
                null
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
