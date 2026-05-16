package com.isko_d.isko_d.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class branchLog {
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String productName;
    private String price;
    private String amount;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    public branchLog() {}

    public branchLog(
        String productName,
        String price,
        String amount
    ) {
        this.productName = productName;
        this.price = price;
        this.amount = amount;
    }

    public Long getId() { return id; }
    public String getProductName() { return productName; }
    public String getPrice() { return price; }
    public String getAmount() { return amount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setActionType(String actionType) { this.actionType = actionType; }
    public void setLocation(String location) { this.location = location; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
}



