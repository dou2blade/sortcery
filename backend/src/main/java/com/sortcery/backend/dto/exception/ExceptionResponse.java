package com.sortcery.backend.dto.exception;

import java.util.Map;
import java.time.Instant;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;

public abstract class ExceptionResponse {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ExceptionResponse(int status, String error, String message, HttpServletRequest request) {
        this.timestamp = Instant.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = request.getRequestURI();
    }

    public Instant getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getMessage() { return message; }
    public String getPath() { return path; }

    public ResponseEntity<Map<String, ?>> build() {
        return ResponseEntity.status(status)
            .body(Map.of(
                        "timestamp", timestamp,
                        "status", status,
                        "error", error,
                        "message", message,
                        "path", path
                        ));
    }
}
