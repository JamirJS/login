package com.project.exception;

public class RoleEntityNotFoundException extends RuntimeException {
    public RoleEntityNotFoundException(String message) {
        super(message);
    }
}
