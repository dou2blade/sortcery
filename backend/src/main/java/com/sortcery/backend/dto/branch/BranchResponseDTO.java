package com.sortcery.backend.dto.branch;

import com.sortcery.backend.model.Branch;
import com.sortcery.backend.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class BranchResponseDTO {
    private final Long id;
    private final Long storeId;
    private final List<Long> retailerIds;
    private final List<Long> managerIds;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public BranchResponseDTO(Branch branch) {
        this.id = branch.getId();
        this.storeId = branch.getStore().getId();
        this.retailerIds = branch.getRetailers()
            .stream()
            .map(User::getId)
            .toList();
        this.managerIds = branch.getManagers()
            .stream()
            .map(User::getId)
            .toList();
        this.name = branch.getName();
        this.createdAt = branch.getCreatedAt();
        this.updatedAt = branch.getUpdatedAt();
    }

    public Long getId() { return id; }
    public Long getStoreId() { return storeId; }
    public List<Long> getRetailerIds() { return retailerIds; }
    public List<Long> getManagerIds() { return managerIds; }
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
