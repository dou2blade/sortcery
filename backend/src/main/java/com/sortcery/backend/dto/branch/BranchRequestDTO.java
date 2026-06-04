package com.sortcery.backend.dto.branch;

import java.util.List;

import com.sortcery.backend.validation.Create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BranchRequestDTO {

    @NotBlank(groups=Create.class)
    private String name;

    @NotNull(groups = Create.class)
    private Long storeId;

    private List<Long> userIds;

    public String getName() { return name; }
    public Long getStoreId() { return storeId; }
    public List<Long> getUserIds() { return userIds; }

    public void setName(String name) { this.name = name; }
    public void setStoreId(Long storeId) { this.storeId = storeId; }
    public void setUserIds(List<Long> userIds) { this.userIds = userIds; }
}
