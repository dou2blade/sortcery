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

    @NotNull(groups = Create.class)
    private Double latitude;

    @NotNull(groups = Create.class)
    private Double longitude;

    private List<Long> userIds;

    public String getName() { return name; }
    public Long getStoreId() { return storeId; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public List<Long> getUserIds() { return userIds; }

    public void setName(String name) { this.name = name; }
    public void setStoreId(Long storeId) { this.storeId = storeId; }
    public void setUserIds(List<Long> userIds) { this.userIds = userIds; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}
