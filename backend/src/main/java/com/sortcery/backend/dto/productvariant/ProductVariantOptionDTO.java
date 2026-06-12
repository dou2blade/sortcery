package com.sortcery.backend.dto.productvariant;

import com.sortcery.backend.model.ProductVariant;

public class ProductVariantOptionDTO {
    private final Long id;
    private final String name;
    private final String productName;

    public ProductVariantOptionDTO(ProductVariant productVariant) {
        this.id = productVariant.getId();
        this.name = productVariant.getName();
        this.productName = productVariant.getProduct().getName();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getProductName() { return productName; }
}

