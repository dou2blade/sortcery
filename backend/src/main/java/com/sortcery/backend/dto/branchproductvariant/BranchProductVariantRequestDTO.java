package com.sortcery.backend.dto.branchproductvariant;

import java.math.BigDecimal;

import com.sortcery.backend.validation.Create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BranchProductVariantRequestDTO {

    @NotNull(groups = Create.class)
    private Long productVariantId;

    @NotBlank(groups=Create.class)
    private String sku;

    @Min(value=0)
    private BigDecimal price;

    @Min(value=0)
    private Integer quantity;

    public Long getProductVariantId() { return productVariantId; }
    public String getSku() { return sku; }
    public BigDecimal getPrice() { return price; }
    public Integer getQuantity() { return quantity; }

    public void setProductVariantId(Long productVariantId) { this.productVariantId = productVariantId; }
    public void setSku(String sku) { this.sku = sku; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
