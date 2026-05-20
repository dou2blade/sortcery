package com.sortcery.backend.dto.branch;

import com.sortcery.backend.validation.Create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BranchRequestDTO {

    @NotBlank(groups=Create.class)
    private String name;

    @NotNull(groups = Create.class)
    private Long storeId;

    @NotNull(groups = Create.class)
    private Long userId;

    public String getName() { return name; }

    public Long getStoreId() { return storeId; }

    public Long getUserId() { return userId; }

    public void setName(String name) { this.name = name; }

    public void setStoreId(Long storeId) { this.storeId = storeId; }

    public void setUserId(Long userId) { this.userId = userId; }
}