package com.sortcery.backend.dto.common;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

public class ApiResponse<T> {
    private final List<T> data;
    private final Instant timestamp;
    private final Map<String, Object> pagination; 

    public ApiResponse(T content) {
        this.data = List.of(content);

        this.timestamp = Instant.now();

        this.pagination = new HashMap<String, Object>();
        this.pagination.put("page", 0);
        this.pagination.put("size", this.data.size());
        this.pagination.put("totalElements", this.data.size());
        this.pagination.put("totalPages", 1);
        this.pagination.put("last", true);
    }

    public ApiResponse(List<T> content) {
        this.data = content;

        this.timestamp = Instant.now();

        this.pagination = new HashMap<String, Object>();
        this.pagination.put("page", 0);
        this.pagination.put("size", content.size());
        this.pagination.put("totalElements", content.size());
        this.pagination.put("totalPages", 1);
        this.pagination.put("last", true);
    }

    public ApiResponse(Page<T> page) {
        this.data = page.getContent();

        this.timestamp = Instant.now();

        this.pagination = new HashMap<String, Object>();
        this.pagination.put("page", page.getNumber());
        this.pagination.put("size", page.getSize());
        this.pagination.put("totalElements", page.getTotalElements());
        this.pagination.put("totalPages", page.getTotalPages());
        this.pagination.put("last", page.isLast());
    }

    public List<T> getData() { return data; }
    public Instant getTimestamp() { return timestamp; }
    public Map<String, Object> getPagination() { return pagination; }
}
