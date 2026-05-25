package com.sortcery.backend.dto.branchstock;

import com.sortcery.backend.validation.Create;
import jakarta.validation.constraints.NotNull;

public class BranchStockRequestDTO {

    @NotNull(groups=Create.class)
    private Integer amount;

    @NotNull(groups = Create.class)
    private Long productId;

    @NotNull(groups = Create.class)
    private Long branchId;

    public Integer getAmount() { return amount; }

    public Long getProductId() { return productId; }

    public Long getBranchId() { return branchId; }

    public void setAmount(Integer amount) { this.amount = amount; }

    public void setProductId(Long productId) { this.productId = productId; }

    public void setBranchId(Long branchId) { this.branchId = branchId; }
}