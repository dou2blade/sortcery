package com.sortcery.backend.dto.inventorymovement;

import com.sortcery.backend.model.InventoryMovement;
import com.sortcery.backend.validation.Create;

import jakarta.validation.constraints.NotNull;

public class InventoryMovementRequestDTO {

    @NotNull(groups=Create.class)
    private Long branchProductVariantId;

    @NotNull(groups=Create.class)
    private InventoryMovement.Type type;

    @NotNull(groups=Create.class)
    private Integer quantityChange;

    private String notes;

    @NotNull(groups=Create.class)
    private Long createdById;

    public Long getBranchProductVariantId() { return branchProductVariantId; }
    public InventoryMovement.Type getType() { return type; }
    public Integer getQuantityChange() { return quantityChange; }
    public String getNotes() { return notes; }
    public Long getCreatedById() { return createdById; }


    public void setBranchProductVariantId(Long branchProductVariantId) { this.branchProductVariantId = branchProductVariantId; }
    public void setType(InventoryMovement.Type type) { this.type = type; }
    public void setQuantityChange(Integer quantityChange) { this.quantityChange = quantityChange; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setCreatedById(Long createdById) { this.createdById = createdById; }
}
