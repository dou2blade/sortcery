package com.sortcery.backend.dto.branchstock;

import java.time.LocalDateTime;

import com.sortcery.backend.model.BranchStock;

public class BranchStockResponseDTO {
    private final Long id;
    private final Long productId;
    private final Long branchId;
    private final Integer amount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public BranchStockResponseDTO(BranchStock branchStock) {
        this.id = branchStock.getId();
        this.productId = branchStock.getProduct().getId();
        this.branchId = branchStock.getBranch().getId();
        this.amount = branchStock.getAmount();
        this.createdAt = branchStock.getCreatedAt();
        this.updatedAt = branchStock.getUpdatedAt();
    }

    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public Long getBranchId() { return branchId; }
    public Integer getAmount() { return amount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}