package org.kataolympp.exception;

public class TodoListItemDatabaseInsertException extends RuntimeException {
    public TodoListItemDatabaseInsertException() {
        super("Failed to insert TodoListItem into the database");
    }
}