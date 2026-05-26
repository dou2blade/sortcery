package com.sortcery.backend.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="products")
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

    private String name;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    public Product() {}

    public Product(
        ProductCategory productCategory,
        Brand brand,
        String name
    ) {
        this.productCategory = productCategory;
        this.brand = brand;
        this.name = name;
    }

    public Long getId() { return id; }
    public ProductCategory getProductCategory() { return productCategory; }
    public Brand getBrand() { return brand; }
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setProductCategory(ProductCategory productCategory) { this.productCategory = productCategory; }
    public void setBrand(Brand brand) { this.brand = brand; }
    public void setName(String name) { this.name = name; }
}
