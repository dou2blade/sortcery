package com.sortcery.backend.dto.product;

import com.sortcery.backend.model.Product;

public class ProductPublicDTO {
    private final Long id;
    private final Long productCategoryId;
    private final Long brandId;

    private final String name;
    private final String productCategoryName;
    private final String brandName;

    private final String imageUrl;

    public ProductPublicDTO(Product product) {
        this.id = product.getId();
        this.productCategoryId = product.getProductCategory().getId();
        this.brandId = product.getBrand().getId();

        this.name = product.getName();
        this.productCategoryName = product.getProductCategory().getName();
        this.brandName = product.getBrand().getName();

        this.imageUrl = product.getProductVariants().getFirst().getImageUrl();
    }

    public Long getId() { return id; }
    public Long getProductCategoryId() { return productCategoryId; }
    public Long getBrandId() { return brandId; }

    public String getName() { return name; }
    public String getProductCategoryName() { return productCategoryName; }
    public String getBrandName() { return brandName; }

    public String getImageUrl() { return imageUrl; }
}
