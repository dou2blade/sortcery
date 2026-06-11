package com.sortcery.backend.dto.product;

import com.sortcery.backend.model.Product;

public class ProductOptionDTO {
    private final Long id;
    private final String name;

    public ProductOptionDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}

