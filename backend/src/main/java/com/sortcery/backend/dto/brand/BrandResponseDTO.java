package com.sortcery.backend.dto.brand;

import com.sortcery.backend.dto.product.ProductOptionDTO;
import com.sortcery.backend.model.Brand;
import java.time.LocalDateTime;
import java.util.List;

public class BrandResponseDTO {
    private final Long id;
    private final String name;
    private final String imageUrl;
    private final List<ProductOptionDTO> products;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public BrandResponseDTO(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.imageUrl = brand.getImageUrl();
        this.products = brand.getProducts()
            .stream()
            .map(ProductOptionDTO::new)
            .toList();
        this.createdAt = brand.getCreatedAt();
        this.updatedAt = brand.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
    public List<ProductOptionDTO> getProducts() { return products; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
