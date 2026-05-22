package com.sortcery.backend.dto.productcategory;

import com.sortcery.backend.validation.Create;

import jakarta.validation.constraints.NotBlank;

public class ProductCategoryRequestDTO {

    @NotBlank(groups=Create.class)
    private String name;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}