package com.sortcery.backend.dto.branchproductvariant;

import com.sortcery.backend.model.BranchProductVariant;

import java.math.BigDecimal;

public class BranchProductVariantPublicDTO {
    private final Long id;

    private final Long storeId;
    private final String storeName;

    private final Long branchId;
    private final String branchName;

    private final Long productId;
    private final String productName;

    private final Long productVariantId;
    private final String productVariantName;
    private final String productVariantImageUrl;

    private final BigDecimal price;
    private final Integer quantity;

    private final Long sales;
    private final Double distance;

    public BranchProductVariantPublicDTO(BranchProductVariant branchProductVariant, Long sales, Double distance) {
		this.id = branchProductVariant.getId();

		this.storeId = branchProductVariant.getBranch().getStore().getId();
		this.storeName = branchProductVariant.getBranch().getStore().getName();

		this.branchId = branchProductVariant.getBranch().getId();
		this.branchName = branchProductVariant.getBranch().getName();

        this.productId = branchProductVariant.getProductVariant().getProduct().getId();
        this.productName = branchProductVariant.getProductVariant().getProduct().getName();

		this.productVariantId = branchProductVariant.getProductVariant().getId();
        this.productVariantName = branchProductVariant.getProductVariant().getName();
        this.productVariantImageUrl = branchProductVariant.getProductVariant().getImageUrl();

		this.price = branchProductVariant.getPrice();
		this.quantity = branchProductVariant.getQuantity();

        this.sales = sales;
        this.distance = distance;
    }

    public Long getId() { return id; }

    public Long getStoreId() { return storeId; }
    public String getStoreName() { return storeName; }

    public Long getBranchId() { return branchId; }
    public String getBranchName() { return branchName; }

    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }

    public Long getProductVariantId() { return productVariantId; }
    public String getProductVariantName() { return productVariantName; }
    public String getProductVariantImageUrl() { return productVariantImageUrl; }

    public BigDecimal getPrice() { return price; }
    public Integer getQuantity() { return quantity; }

    public Long getSales() { return sales; }
    public Double getDistance() { return distance; }
}
