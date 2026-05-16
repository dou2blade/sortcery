package com.sortcery.backend.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Class<?> entity, Long id) {
        super(String.format("%s with %d id not found.", entity.getSimpleName(), id));
    }

    public NotFoundException(Class<?> entity, Object value, String field) {
        super(String.format("%s with %s of %s not found.", entity.getSimpleName(), field, (String)value));
    }

    public NotFoundException(String message) {
        super(message);
    }
}
