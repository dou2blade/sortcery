package com.sortcery.backend.dto.branchuser;

import com.sortcery.backend.model.BranchUser;

public class BranchUserResponseDTO {
    private final Long branchId;
    private final Long userId;

    public BranchUserResponseDTO(BranchUser branchUser) {
        this.branchId = branchUser.getBranch().getId();
        this.userId = branchUser.getUser().getId();
    }

    public Long getBranchId() { return branchId; }
    public Long getUserId() { return userId; }
}