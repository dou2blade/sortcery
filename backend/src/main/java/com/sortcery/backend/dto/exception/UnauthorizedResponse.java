package com.sortcery.backend.dto.exception;

import jakarta.servlet.http.HttpServletRequest;

public class UnauthorizedResponse extends ExceptionResponse {
    public UnauthorizedResponse(String message, HttpServletRequest request) {
        super(401, "Unauthorized", message, request);
    }
}
