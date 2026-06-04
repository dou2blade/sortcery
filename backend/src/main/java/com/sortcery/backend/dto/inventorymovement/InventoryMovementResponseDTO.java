package com.sortcery.backend.dto.inventorymovement;

import com.sortcery.backend.model.InventoryMovement;

import java.time.LocalDateTime;

public class InventoryMovementResponseDTO {
    private Long id;
    private Long branchProductVariantId;
    private InventoryMovement.Type type;
    private Integer quantityChange;
    private Integer newQuantity;
    private String notes;
    private Long createdById;
    private LocalDateTime createdAt;

    public InventoryMovementResponseDTO(InventoryMovement inventoryMovement) {
        this.id  = inventoryMovement.getId();
        this.branchProductVariantId = inventoryMovement.getBranchProductVariant().getId();
        this.type = inventoryMovement.getType();
        this.quantityChange = inventoryMovement.getQuantityChange();
        this.newQuantity = inventoryMovement.getNewQuantity();
        this.notes = inventoryMovement.getNotes();
        this.createdById = inventoryMovement.getCreatedBy().getId();
        this.createdAt = inventoryMovement.getCreatedAt();
    }

    public Long getId() { return id; }
    public Long getBranchProductVariantId() { return branchProductVariantId; }
    public InventoryMovement.Type getType() { return type; }
    public Integer getQuantityChange() { return quantityChange; }
    public Integer getNewQuantity() { return newQuantity; }
    public String getNotes() { return notes; }
    public Long getCreatedById() { return createdById; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
