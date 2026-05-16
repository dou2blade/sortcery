package com.sortcery.backend.dto.exception;

import jakarta.servlet.http.HttpServletRequest;

public class ServerErrorResponse extends ExceptionResponse {
    public ServerErrorResponse(String message, HttpServletRequest request) {
        super(500, "Internal Server Error", message, request);
    }
}
