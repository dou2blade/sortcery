package com.sortcery.backend.dto.store;

import com.sortcery.backend.dto.branch.BranchSummaryDTO;
import com.sortcery.backend.model.Store;
import java.time.LocalDateTime;
import java.util.List;

public class StoreResponseDTO {
    private final Long id;
    private final String name;
    private final List<BranchSummaryDTO> branches;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public StoreResponseDTO(Store store) {
        this.id = store.getId();
        this.name = store.getName();

        this.branches = store.getBranches()
            .stream()
            .map(BranchSummaryDTO::new)
            .toList();

        this.createdAt = store.getCreatedAt();
        this.updatedAt = store.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<BranchSummaryDTO> getBranches() { return branches; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}

