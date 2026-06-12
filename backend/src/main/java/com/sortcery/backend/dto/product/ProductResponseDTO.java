package com.sortcery.backend.dto.product;

import com.sortcery.backend.dto.productvariant.ProductVariantResponseDTO;
import com.sortcery.backend.model.Product;
import java.time.LocalDateTime;
import java.util.List;

public class ProductResponseDTO {
    private final Long id;
    private final Long productCategoryId;
    private final Long brandId;
    private final String name;
    private final String imageUrl;
    private final List<ProductVariantResponseDTO> productVariants;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.productCategoryId = product.getProductCategory().getId();
        this.brandId = product.getBrand().getId();
        this.name = product.getName();
        this.imageUrl = product.getImageUrl();
        this.productVariants = product.getProductVariants()
            .stream()
            .map(ProductVariantResponseDTO::new)
            .toList();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }

    public Long getId() { return id; }
    public Long getProductCategoryId() { return productCategoryId; }
    public Long getBrandId() { return brandId; }
    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
    public List<ProductVariantResponseDTO> getProductVariants() { return productVariants; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
