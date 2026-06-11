package com.sortcery.backend.dto.branch;

import com.sortcery.backend.model.Branch;

public class BranchSummaryDTO {
    private final Long id;
    private final Long storeId;
    private final String name;
    private final String storeName;

    public BranchSummaryDTO(Branch branch) {
        this.id = branch.getId();
        this.storeId = branch.getStore().getId();
        this.name = branch.getName();
        this.storeName = branch.getStore().getName();
    }

    public Long getId() { return id; }
    public Long getStoreId() { return storeId; }
    public String getName() { return name; }
    public String getStoreName() { return storeName; }
}
