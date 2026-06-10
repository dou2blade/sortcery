package com.sortcery.backend.dto.branch;

import java.util.ArrayList;
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
    private String address;

    @NotNull(groups = Create.class)
    private Double latitude;

    @NotNull(groups = Create.class)
    private Double longitude;

    private List<Long> managerIds = new ArrayList<>();
    private List<Long> retailerIds = new ArrayList<>();

    public String getName() { return name; }
    public Long getStoreId() { return storeId; }
    public String getAddress() { return address; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public List<Long> getManagerIds() { return managerIds; }
    public List<Long> getRetailerIds() { return retailerIds; }

    public void setName(String name) { this.name = name; }
    public void setStoreId(Long storeId) { this.storeId = storeId; }
    public void setAddress(String address) { this.address = address; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public void setManagerIds(List<Long> managerIds) { this.managerIds = managerIds; }
    public void setRetailerIds(List<Long> retailerIds) { this.retailerIds = retailerIds; }
}
