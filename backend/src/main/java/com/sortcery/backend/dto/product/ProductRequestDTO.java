package com.sortcery.backend.dto.product;

import com.sortcery.backend.validation.Create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductRequestDTO {

    @NotBlank(groups=Create.class)
    private String name;
    
    private String imageUrl;

    @NotNull(groups = Create.class)
    private Long productCategoryId;

    @NotNull(groups = Create.class)
    private Long brandId;

    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
    public Long getProductCategoryId() { return productCategoryId; }
    public Long getBrandId() { return brandId; }

    public void setName(String name) { this.name = name; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setProductCategoryId(Long productCategoryId) { this.productCategoryId = productCategoryId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }
}
