package com.sortcery.backend.dto.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ValidationResponse extends ExceptionResponse {

    private final Map<String, String> errors;

    public ValidationResponse(
        String message,
        Map<String, String> errors,
        HttpServletRequest request
    ) {
        super(400, "Bad Request", message, request);
        this.errors = errors;
    }

    @Override
    public ResponseEntity<Map<String, ?>> build() {
        return ResponseEntity.badRequest()
            .body(Map.of(
                "timestamp", getTimestamp(),
                "status", getStatus(),
                "error", getError(),
                "message", getMessage(),
                "path", getPath(),
                "errors", errors
            ));
    }
}
