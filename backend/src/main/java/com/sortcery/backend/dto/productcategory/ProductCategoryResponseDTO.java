package com.sortcery.backend.dto.productcategory;

import com.sortcery.backend.model.ProductCategory;
import java.time.LocalDateTime;

public class ProductCategoryResponseDTO {
    private final Long id;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ProductCategoryResponseDTO(ProductCategory productCategory) {
        this.id = productCategory.getId();
        this.name = productCategory.getName();
        this.createdAt = productCategory.getCreatedAt();
        this.updatedAt = productCategory.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
