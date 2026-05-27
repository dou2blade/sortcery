package com.sortcery.backend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CheckConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "branch_product_variants",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"branch_id", "product_id"}),
        @UniqueConstraint(columnNames = {"branch_id", "sku"})
    }
)
@EntityListeners(AuditingEntityListener.class)
public class BranchProductVariant {
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;

    @Column(nullable = false)
    private String sku;

    @Column(
        precision = 10, 
        scale = 2, 
        nullable = false, 
        check = @CheckConstraint(constraint = "price >= 0")
    )
    private BigDecimal price;

    @Column(
        nullable = false, 
        check = @CheckConstraint(constraint = "quantity >= 0")
    )
    private Integer quantity = 0;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public BranchProductVariant() {}

    public BranchProductVariant(
        Branch branch,
        ProductVariant productVariant,
        String sku,
        BigDecimal price,
        Integer quantity
    ) {
        this.branch = branch;
        this.productVariant = productVariant;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() { return id; }
    public Branch getBranch() { return branch; }
    public ProductVariant getProductVariant() { return productVariant; }
    public String getSku() { return sku; }
    public BigDecimal getPrice() { return price; }
    public Integer getQuantity() { return quantity; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setBranch(Branch branch) { this.branch = branch; }
    public void setProductVariant(ProductVariant productVariant) { this.productVariant = productVariant; }
    public void setSku(String sku) { this.sku = sku; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
