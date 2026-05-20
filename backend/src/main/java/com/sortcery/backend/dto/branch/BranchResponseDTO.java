package com.sortcery.backend.dto.branch;

import com.sortcery.backend.model.Branch;
import java.time.LocalDateTime;

public class BranchResponseDTO {
    private final Long id;
    private final Long storeId;
    private final Long userId;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public BranchResponseDTO(Branch branch) {
        this.id = branch.getId();
        this.storeId = branch.getStore().getId();
        this.userId = branch.getUser().getId();
        this.name = branch.getName();
        this.createdAt = branch.getCreatedAt();
        this.updatedAt = branch.getUpdatedAt();
    }

    public Long getId() { return id; }
    public Long getStoreId() { return storeId; }
    public Long getUserId() { return userId; }
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
