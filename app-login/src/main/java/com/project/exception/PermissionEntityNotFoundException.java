package com.project.exception;

public class PermissionEntityNotFoundException extends RuntimeException {
    public PermissionEntityNotFoundException(String message) {
        super(message);
    }
}
