package com.sortcery.backend.dto.branchproductvariant;

import com.sortcery.backend.model.BranchProductVariant;

public class BranchProductVariantOptionDTO {
    private final Long id;
    private final String productName;
    private final String productVariantName;
    private final String sku;

    public BranchProductVariantOptionDTO(BranchProductVariant branchProductVariant) {
        this.id = branchProductVariant.getId();
        this.productName = branchProductVariant.getProductVariant().getProduct().getName();
        this.productVariantName = branchProductVariant.getProductVariant().getName();
        this.sku = branchProductVariant.getSku();
    }

    public Long getId() { return id; }
	public String getProductName() { return productName; }
	public String getProductVariantName() { return productVariantName; }
	public String getSku() { return sku; }
}

