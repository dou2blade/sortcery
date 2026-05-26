package com.sortcery.backend.dto.branchuser;

import com.sortcery.backend.validation.Create;
import jakarta.validation.constraints.NotNull;

public class BranchUserRequestDTO {

    @NotNull(groups=Create.class)

    @NotNull(groups = Create.class)
    private Long branchId;

    @NotNull(groups = Create.class)
    private Long userId;

    public Long getBranchId() { return branchId; }

    public Long getUserId() { return userId; }

    public void setBranchId(Long branchId) { this.branchId = branchId; }

    public void setUserId(Long userId) { this.userId = userId; }
}