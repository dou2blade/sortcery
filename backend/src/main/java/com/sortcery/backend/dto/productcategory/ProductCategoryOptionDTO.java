package com.sortcery.backend.dto.productcategory;

import com.sortcery.backend.model.ProductCategory;

public class ProductCategoryOptionDTO {
    private final Long id;
    private final String name;

    public ProductCategoryOptionDTO(ProductCategory productCategory) {
        this.id = productCategory.getId();
        this.name = productCategory.getName();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}

