package com.sortcery.backend.dto.productvariant;

import com.sortcery.backend.model.ProductVariant;
import java.time.LocalDateTime;

public class ProductVariantResponseDTO {
    private final Long id;
    private final Long productId;
    private final String name;
    private final String productName;
    private final String imageUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ProductVariantResponseDTO(ProductVariant productVariant) {
        this.id = productVariant.getId();
        this.productId = productVariant.getProduct().getId();
        this.name = productVariant.getName();
        this.productName = productVariant.getProduct().getName();
        this.imageUrl = productVariant.getImageUrl();
        this.createdAt = productVariant.getCreatedAt();
        this.updatedAt = productVariant.getUpdatedAt();
    }

    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public String getName() { return name; }
    public String getProductName() { return productName; }
    public String getImageUrl() { return imageUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
