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
@Table(name="stores")
@EntityListeners(AuditingEntityListener.class)
public class Store {
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "store")
    private List<Branch> branches = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    public Store() {}

    public Store(
        String name,
        List<Branch> branches
    ) {
        this.name = name;
        this.branches = branches;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<Branch> getBranches() { return branches; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setBranches(List<Branch> branches) { this.branches = branches; }

    public void addBranch(Branch branch) {
        if (!branches.contains(branch)) {
            branches.add(branch);
        }

        if (branch.getStore() != this) {
            branch.setStore(this);
        }
    }

    public void transferBranch(Branch branch, Store newStore) {
        if (!branches.contains(branch)) {
            throw new IllegalArgumentException("Branch does not belong to this store");
        }

        branches.remove(branch);
        newStore.addBranch(branch);
    }
}
