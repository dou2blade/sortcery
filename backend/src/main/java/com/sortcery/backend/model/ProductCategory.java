package com.sortcery.backend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="product_categories")
@EntityListeners(AuditingEntityListener.class)
public class ProductCategory {
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "productCategory")
    private List<Product> products = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    public ProductCategory() {}

    public ProductCategory(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<Product> getProducts() { return products; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setProducts(List<Product> products) { this.products = products; }

    public void addProduct(Product product) {
        if (!products.contains(product)) {
            products.add(product);
        }

        if (product.getProductCategory() != this) {
            product.setProductCategory(this);
        }
    }

    public void removeProduct(Product product) {
        if (!products.contains(product)) {
            throw new IllegalArgumentException("Product does not belong to this category");
        }

        products.remove(product);
        product.setProductCategory(null);
    }
}
