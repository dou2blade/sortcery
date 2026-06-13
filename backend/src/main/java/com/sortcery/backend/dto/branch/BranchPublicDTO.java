package com.sortcery.backend.dto.branch;

import com.sortcery.backend.model.Branch;

public class BranchPublicDTO {
    private final Long id;
    private final String name;

    private final Long storeId;
    private final String storeName;

    private final String address;
    private final Double distance;

    public BranchPublicDTO(Branch branch, Double distance) {
        this.id = branch.getId();
        this.name = branch.getName();

        this.storeId = branch.getStore().getId();
        this.storeName = branch.getStore().getName();

        this.address = branch.getAddress();
        this.distance = distance;
    }

    public Long getId() { return id; }
    public String getName() { return name; }

    public Long getStoreId() { return storeId; }
    public String getStoreName() { return storeName; }

    public String getAddress() { return address; }
    public Double getDistance() { return distance; }
}
