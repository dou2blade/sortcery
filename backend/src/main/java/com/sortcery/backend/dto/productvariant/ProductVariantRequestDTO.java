package com.sortcery.backend.dto.productvariant;

import com.sortcery.backend.validation.Create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductVariantRequestDTO {

    @NotNull(groups = Create.class)
    private Long productId;

    @NotBlank(groups=Create.class)
    private String name;

    private String imageUrl;

    public Long getProductId() { return productId; }
    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }

    public void setProductId(Long productId) { this.productId = productId; }
    public void setName(String name) { this.name = name; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
