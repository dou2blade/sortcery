package com.sortcery.backend.dto.brand;

import com.sortcery.backend.model.Brand;
import java.time.LocalDateTime;

public class BrandResponseDTO {
    private final Long id;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public BrandResponseDTO(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.createdAt = brand.getCreatedAt();
        this.updatedAt = brand.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
