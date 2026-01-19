package com.project.exception;

public class UserInUseException extends RuntimeException {
    public UserInUseException(String message) {
        super(message);
    }
}
