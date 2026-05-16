package com.sortcery.backend.dto.store;

import com.sortcery.backend.model.Store;
import java.time.LocalDateTime;

public class StoreResponseDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public StoreResponseDTO(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.createdAt = store.getCreatedAt();
        this.updatedAt = store.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getStoreName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}

