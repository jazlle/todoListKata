package org.kataolympp.exception;

public class TodoListItemNotFoundException extends RuntimeException {
    public TodoListItemNotFoundException(Long id) {
        super("TodoList item not found with id: " + id);
    }
}