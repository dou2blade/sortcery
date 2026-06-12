package com.sortcery.backend.dto.branchproductvariant;

import com.sortcery.backend.model.BranchProductVariant;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BranchProductVariantResponseDTO {
    private final Long id;
    private final Long branchId;
    private final Long productId;
    private final String productName;
    private final Long productVariantId;
    private final String productVariantName;
    private final String sku;
    private final BigDecimal price;
    private final Integer quantity;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public BranchProductVariantResponseDTO(BranchProductVariant branchProductVariant) {
		this.id = branchProductVariant.getId();
		this.branchId = branchProductVariant.getBranch().getId();
        this.productId = branchProductVariant.getProductVariant().getProduct().getId();
        this.productName = branchProductVariant.getProductVariant().getProduct().getName();
		this.productVariantId = branchProductVariant.getProductVariant().getId();
        this.productVariantName = branchProductVariant.getProductVariant().getName();
		this.sku = branchProductVariant.getSku();
		this.price = branchProductVariant.getPrice();
		this.quantity = branchProductVariant.getQuantity();
		this.createdAt = branchProductVariant.getCreatedAt();
		this.updatedAt = branchProductVariant.getUpdatedAt();
    }

    public Long getId() { return id; }
    public Long getBranchId() { return branchId; }
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public Long getProductVariantId() { return productVariantId; }
    public String getProductVariantName() { return productVariantName; }
    public String getSku() { return sku; }
    public BigDecimal getPrice() { return price; }
    public Integer getQuantity() { return quantity; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
