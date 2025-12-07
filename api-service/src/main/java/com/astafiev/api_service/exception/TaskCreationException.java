package com.astafiev.api_service.exception;

public class TaskCreationException extends Exception {
    public TaskCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskCreationException(String message) {
        super(message);
    }
}
