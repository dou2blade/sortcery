package com.sortcery.backend.dto.inventorymovement;

import com.sortcery.backend.model.BranchProductVariant;
import com.sortcery.backend.model.InventoryMovement;
import com.sortcery.backend.model.ProductVariant;

import java.time.LocalDateTime;

public class InventoryMovementResponseDTO {
    private Long id;

    private Long productId;
    private String productName;

    private Long productVariantId;
    private String productVariantName;

    private Long branchProductVariantId;
    private String sku;

    private InventoryMovement.Type type;
    private Integer quantityChange;
    private Integer newQuantity;
    private String notes;

    private Long createdById;
    private String createdByEmail;
    private LocalDateTime createdAt;

    public InventoryMovementResponseDTO(InventoryMovement inventoryMovement) {
        BranchProductVariant bpv = inventoryMovement.getBranchProductVariant();
        ProductVariant pv = bpv.getProductVariant();

        this.id  = inventoryMovement.getId();

        this.productId = pv.getProduct().getId();
        this.productName = pv.getProduct().getName();

        this.productVariantId = pv.getId();
        this.productVariantName = pv.getName();

        this.branchProductVariantId = bpv.getId();
        this.sku = bpv.getSku();

        this.type = inventoryMovement.getType();
        this.quantityChange = inventoryMovement.getQuantityChange();
        this.newQuantity = inventoryMovement.getNewQuantity();
        this.notes = inventoryMovement.getNotes();

        this.createdById = inventoryMovement.getCreatedBy().getId();
        this.createdByEmail = inventoryMovement.getCreatedBy().getEmail();
        this.createdAt = inventoryMovement.getCreatedAt();
    }

    public Long getId() { return id; }

    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }

    public Long getProductVariantId() { return productVariantId; }
    public String getProductVariantName() { return productVariantName; }

    public Long getBranchProductVariantId() { return branchProductVariantId; }
    public String getSku() { return sku; }

    public InventoryMovement.Type getType() { return type; }
    public Integer getQuantityChange() { return quantityChange; }
    public Integer getNewQuantity() { return newQuantity; }
    public String getNotes() { return notes; }

    public Long getCreatedById() { return createdById; }
    public String getCreatedByEmail() { return createdByEmail; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
