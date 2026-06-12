package com.sortcery.backend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "products",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"brand_id", "name"})
    }
)
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @OneToMany(
        mappedBy = "product",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<ProductVariant> productVariants = new ArrayList<>();

    private String name;
    private String imageUrl;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Product() {}

    public Product(
        ProductCategory productCategory,
        Brand brand,
        String name,
        String imageUrl
    ) {
        this.productCategory = productCategory;
        this.brand = brand;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public ProductCategory getProductCategory() { return productCategory; }
    public Brand getBrand() { return brand; }
    public List<ProductVariant> getProductVariants() { return productVariants; }
    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setProductCategory(ProductCategory productCategory) { this.productCategory = productCategory; }
    public void setBrand(Brand brand) { this.brand = brand; }
    public void setName(String name) { this.name = name; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public void setProductVariants(List<ProductVariant> productVariants) { 
        this.productVariants.clear();

        for (ProductVariant variant : productVariants) {
            addProductVariant(variant);
        }
    } 

    public void addProductVariant(ProductVariant productVariant) {
        if (!productVariants.contains(productVariant)) {
            productVariants.add(productVariant);
        }

        if (productVariant.getProduct() != this) {
            productVariant.setProduct(this);
        }
    }

    public void removeProductVariant(ProductVariant productVariant) {
        if (!productVariants.contains(productVariant)) {
            throw new IllegalArgumentException("Product Variant does not belong to this product");
        }

        productVariants.remove(productVariant);
    }
}
