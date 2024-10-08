package com.Jeneric_Java.calendarappapi.exception;

import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleItemNotFoundException(NoResultsFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handlePropertyValueException(PropertyValueException exception) {
        return new ResponseEntity<>("Bad Request! Event must contain title, location, type, closestCity, and startDate", HttpStatus.BAD_REQUEST);
    }
}
