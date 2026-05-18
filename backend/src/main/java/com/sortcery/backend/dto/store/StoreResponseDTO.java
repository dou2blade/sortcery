package com.sortcery.backend.dto.store;

import com.sortcery.backend.model.Store;
import java.time.LocalDateTime;

public class StoreResponseDTO {
    private final Long id;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public StoreResponseDTO(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.createdAt = store.getCreatedAt();
        this.updatedAt = store.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}

