package com.sortcery.backend.exception;

import com.sortcery.backend.dto.exception.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, ?>> handleNotFound(NotFoundException exception, HttpServletRequest request) {
        return new NotFoundResponse(exception.getMessage(), request).build();
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, ?>> handleUnauthorized(UnauthorizedException exception, HttpServletRequest request) {
        return new UnauthorizedResponse(exception.getMessage(), request).build();
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, ?>> handleForbidden(ForbiddenException exception, HttpServletRequest request) {
        return new ForbiddenResponse(exception.getMessage(), request).build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, ?>> handleAccessDenied(AccessDeniedException exception, HttpServletRequest request) {
        return new ForbiddenResponse("Access denied: " + exception.getMessage(), request).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, ?>> handleGenericException(Exception exception, HttpServletRequest request) {
        return new ServerErrorResponse(exception.getMessage(), request).build();
    }
}
