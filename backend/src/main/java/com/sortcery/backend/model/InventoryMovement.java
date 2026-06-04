package com.sortcery.backend.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="inventory_movements")
@EntityListeners(AuditingEntityListener.class)
public class InventoryMovement {
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch_product_variant_id", nullable = false)
    private BranchProductVariant branchProductVariant;

    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        STOCK_IN(true),
        TRANSFER_IN(true),
        ADJUSTMENT_IN(true),
        RETURN(true),

        TRANSFER_OUT(false),
        ADJUSTMENT_OUT(false),
        SALE(false),
        DAMAGED(false),
        EXPIRED(false);

        private final boolean positive;

        Type(boolean positive) {
            this.positive = positive;
        }

        public boolean isPositive () {
            return positive;
        }
    }

    private Integer quantityChange;
    private Integer newQuantity;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public InventoryMovement() {}

    public InventoryMovement(
        BranchProductVariant branchProductVariant,
        Type type,
        Integer quantityChange,
        Integer newQuantity,
        String notes,
        User createdBy
    ) {
        this.branchProductVariant = branchProductVariant;
        this.type = type;
        this.quantityChange = quantityChange;
        this.newQuantity = newQuantity;
        this.notes = notes;
        this.createdBy = createdBy;
    }

    public Long getId() { return id; }
    public BranchProductVariant getBranchProductVariant() { return branchProductVariant; }
    public Type getType() { return type; }
    public Integer getQuantityChange() { return quantityChange; }
    public Integer getNewQuantity() { return newQuantity; }
    public String getNotes() { return notes; }
    public User getCreatedBy() { return createdBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setBranchProductVariant(BranchProductVariant branchProductVariant) { this.branchProductVariant = branchProductVariant; }
    public void setType(Type type) { this.type = type; }
    public void setQuantityChange(Integer quantityChange) { this.quantityChange = quantityChange; }
    public void setNewQuantity(Integer newQuantity) { this.newQuantity = newQuantity; }
    public void setNotes(String notes) { this.notes = notes; }
}
