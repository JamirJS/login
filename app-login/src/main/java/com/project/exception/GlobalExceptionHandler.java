package com.project.exception;

import com.project.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({BadCredentialsException.class,
                        UsernameNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleBadCredentials(Exception ex, WebRequest request){
        return buildResponse(ex, HttpStatus.UNAUTHORIZED, request, "Invalid username or password");
    }

    @ExceptionHandler(UserEntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductEntityNotFound(UserEntityNotFoundException ex, WebRequest request){
        return this.buildResponse(ex, HttpStatus.NOT_FOUND, request, "User Not found");
    }

    @ExceptionHandler(PermissionEntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePermissionEntityNotFound(PermissionEntityNotFoundException ex, WebRequest request){
        return this.buildResponse(ex, HttpStatus.NOT_FOUND, request, "Permission Not found");
    }

    @ExceptionHandler(RoleEntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleEntityNotFound(RoleEntityNotFoundException ex, WebRequest request){
        return this.buildResponse(ex, HttpStatus.NOT_FOUND, request, "Role Not found");
    }

    @ExceptionHandler(PermissionInUseException.class)
    public ResponseEntity<ErrorResponse> handlePermissionInUse(PermissionInUseException ex, WebRequest request){
        return this.buildResponse(ex, HttpStatus.CONFLICT, request, "Permission in use");
    }

    @ExceptionHandler(RoleInUseException.class)
    public ResponseEntity<ErrorResponse> handleRoleInUse(RoleInUseException ex, WebRequest request){
        return this.buildResponse(ex, HttpStatus.CONFLICT, request, "Role in use");
    }

    @ExceptionHandler(UserInUseException.class)
    public ResponseEntity<ErrorResponse> handleRoleInUse(UserInUseException ex, WebRequest request){
        return buildResponse(ex, HttpStatus.CONFLICT, request, "User has active relations in the system");
    }

    @ExceptionHandler(RolesRequiredException.class)
    public ResponseEntity<ErrorResponse> handleRolesRequired(RolesRequiredException ex, WebRequest request){
        return this.buildResponse(ex, HttpStatus.CONFLICT, request, "Required roles");
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleRolesRequired(AuthorizationDeniedException ex, WebRequest request){
        logger.warn("Access Denied (Forbidden): {}",ex.getMessage(), ex);

        ErrorResponse errorResponse = this.formatStandarResponseAndPath("Access denied: Insufficient permissions", HttpStatus.FORBIDDEN, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request){
        String message = "Error de integridad de datos.";

        String rootMsg = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        if (rootMsg != null && rootMsg.contains("permissions_permission_name_key")) {
            logger.warn("Attempt to duplicate permissions: {}", rootMsg, ex);
            message = "The permit name is already registered.";
        }
        ErrorResponse errorResponse = formatStandarResponseAndPath(message, HttpStatus.CONFLICT, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        String message = "Error in JSON format";

        // se verifica si la causa es un error de formato de jackson
        if (ex.getCause() instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException) {
            com.fasterxml.jackson.databind.exc.InvalidFormatException formatException = (com.fasterxml.jackson.databind.exc.InvalidFormatException) ex.getCause();

            if (formatException.getTargetType().isEnum()) {
                // Obtenemos el nombre del campo que falló
                String fieldName = formatException.getPath().get(0).getFieldName();

                // En lugar de usar el nombre de la clase Java, usamos un mensaje genérico
                message = String.format("The value provided for the field '%s' is not valid. Please, check the allowed options",
                        fieldName);
            }
        }

        logger.warn("Error de lectura JSON: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = this.formatStandarResponseAndPath(message, HttpStatus.BAD_REQUEST, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse formatStandarResponseAndPath(String error, HttpStatus status, WebRequest request){
        String path = request.getDescription(false).replace("uri=", "");
        ErrorResponse response = new ErrorResponse(LocalDate.now().toString(),
                String.valueOf(status.value()),
                error,
                path);
        return response;
    }

    private ResponseEntity<ErrorResponse> buildResponse(Exception ex, HttpStatus status, WebRequest request, String logMessage) {
        logger.warn("{}: {}", logMessage, ex.getMessage(), ex);
        ErrorResponse errorResponse = this.formatStandarResponseAndPath(ex.getMessage(), status, request);
        return new ResponseEntity<>(errorResponse, status);
    }

}
