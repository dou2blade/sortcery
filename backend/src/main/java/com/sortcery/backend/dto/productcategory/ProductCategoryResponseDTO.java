package com.sortcery.backend.dto.productcategory;

import com.sortcery.backend.dto.product.ProductOptionDTO;
import com.sortcery.backend.model.ProductCategory;
import java.time.LocalDateTime;
import java.util.List;

public class ProductCategoryResponseDTO {
    private final Long id;
    private final String name;
    private final List<ProductOptionDTO> products;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ProductCategoryResponseDTO(ProductCategory productCategory) {
        this.id = productCategory.getId();
        this.name = productCategory.getName();
        this.products = productCategory.getProducts()
            .stream()
            .map(ProductOptionDTO::new)
            .toList();
        this.createdAt = productCategory.getCreatedAt();
        this.updatedAt = productCategory.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<ProductOptionDTO> getProducts() { return products; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
