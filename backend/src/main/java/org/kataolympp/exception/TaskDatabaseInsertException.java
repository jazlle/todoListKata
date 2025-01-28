package org.kataolympp.exception;

public class TaskDatabaseInsertException extends RuntimeException {
    public TaskDatabaseInsertException() {
        super("Failed to insert Task into the database");
    }
}