package org.kataolympp.controller;

import org.kataolympp.exception.TaskDatabaseInsertException;
import org.kataolympp.exception.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskDatabaseInsertException.class)
    public ResponseEntity<Object> handleDatabaseInsertException(TaskDatabaseInsertException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Object> handleItemNotFoundException(TaskNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
