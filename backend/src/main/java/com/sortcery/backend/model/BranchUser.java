package com.sortcery.backend.model;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="branchusers")
@EntityListeners(AuditingEntityListener.class)
public class BranchUser {

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public BranchUser() {}

    public BranchUser(
        Branch branch,
        User user
    ) {
        this.branch = branch;
        this.user = user;
    }

    public Branch getBranch() { return branch; }
    public User getUser() { return user; }

    public void setBranch(Branch branch) { this.branch = branch; }
    public void setUser(User user) { this.user = user; }
}