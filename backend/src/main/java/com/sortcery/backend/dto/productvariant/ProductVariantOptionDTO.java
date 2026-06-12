package com.sortcery.backend.dto.productvariant;

import com.sortcery.backend.model.ProductVariant;

public class ProductVariantOptionDTO {
    private final Long id;
    private final String name;

    public ProductVariantOptionDTO(ProductVariant productVariant) {
        this.id = productVariant.getId();
        this.name = productVariant.getName();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}

