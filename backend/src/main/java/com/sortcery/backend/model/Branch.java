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
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name="branches")
@EntityListeners(AuditingEntityListener.class)
public class Branch {
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    private String name;

    private String address;
    private Double latitude;
    private Double longitude;

    @OneToMany(mappedBy = "branch")
    private List<UserBranch> userBranches = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Branch() {}

    public Branch(
        Store store,
        String name,
        String address,
        Double latitude,
        Double longitude
    ) {
        this.store = store;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() { return id; }
    public Store getStore() { return store; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public List<UserBranch> getUserBranches() { return userBranches; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setStore(Store store) { this.store = store; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    @Transient
    public List<User> getUsers() {
        return userBranches.stream()
            .map(UserBranch::getUser)
            .toList();
    }

    @Transient
    public List<User> getRetailers() {
        return userBranches.stream()
            .map(UserBranch::getUser)
            .filter((user) -> user.getRole() == User.Role.RETAILER)
            .toList();
    }

    @Transient
    public List<User> getManagers() {
        return userBranches.stream()
            .map(UserBranch::getUser)
            .filter((user) -> user.getRole() == User.Role.MANAGER)
            .toList();
    }

}
