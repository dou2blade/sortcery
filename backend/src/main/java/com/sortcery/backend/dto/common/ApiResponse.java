package com.sortcery.backend.dto.common;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

public class ApiResponse {
    private final Object data;
    private final Instant timestamp;
    private final Map<String, Object> meta; 

    public ApiResponse(Object content, Map<String, Object> meta) {
        this.data = content;
        this.timestamp = Instant.now();
        this.meta = meta;
    }

    public static ApiResponse of(Object content) {
        if (content instanceof Page<?> page) {
            Map<String, Object> meta = new HashMap<>();
            meta.put("page", page.getNumber());
            meta.put("size", page.getSize());
            meta.put("totalElements", page.getTotalElements());
            meta.put("totalPages", page.getTotalPages());
            meta.put("last", page.isLast());

            return new ApiResponse(page.getContent(), meta);
        }

        if (content instanceof List<?> list) {
            Map<String, Object> meta = new HashMap<>();
            meta.put("size", list.size());

            return new ApiResponse(list, meta);
        }

        return new ApiResponse(content, null);
    }

    public Object getData() { return data; }
    public Instant getTimestamp() { return timestamp; }
    public Map<String, Object> getMeta() { return meta; }
}
