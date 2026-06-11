package com.sortcery.backend.dto.brand;

import com.sortcery.backend.model.Brand;

public class BrandOptionDTO {
    private final Long id;
    private final String name;

    public BrandOptionDTO(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}

