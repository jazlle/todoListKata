package org.kataolympp.controller;

import org.kataolympp.exception.TodoListItemDatabaseInsertException;
import org.kataolympp.exception.TodoListItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TodoListItemDatabaseInsertException.class)
    public ResponseEntity<Object> handleDatabaseInsertException(TodoListItemDatabaseInsertException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TodoListItemNotFoundException.class)
    public ResponseEntity<Object> handleItemNotFoundException(TodoListItemNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
