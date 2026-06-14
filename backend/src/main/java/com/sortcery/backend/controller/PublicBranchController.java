package com.sortcery.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sortcery.backend.algorithms.Haversine;
import com.sortcery.backend.dto.branch.BranchResponseDTO;
import com.sortcery.backend.dto.common.ApiResponse;
import com.sortcery.backend.service.BranchService;

@RestController
@RequestMapping(path="/public/branches")
public class PublicBranchController {
    private final BranchService branchService;

    public PublicBranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<ApiResponse> findById(
        @PathVariable Long id,
        @RequestParam(required = false) Double latitude,
        @RequestParam(required = false) Double longitude
    ) {
        BranchResponseDTO branch = branchService.findById(id);

        Map<String, Object> data = new HashMap<>();

        data.put("id", branch.getId());
        data.put("name", branch.getName());
        data.put("storeId", branch.getStoreId());
        data.put("storeName", branch.getStoreName());
        data.put("address", branch.getAddress());

        if (latitude != null && longitude != null) {
            data.put(
                "distance",
                Haversine.distance(
                    latitude,
                    longitude,
                    branch.getLatitude(),
                    branch.getLongitude()
                )
            );
        }

        return ResponseEntity.ok(
            ApiResponse.of(data)
        );
    }


    @GetMapping(path="/nearby")
    public ResponseEntity<ApiResponse> findNearby(
        @RequestParam(defaultValue = "15") int size,
        @RequestParam Double latitude,
        @RequestParam Double longitude
    ) {
        return ResponseEntity.ok(ApiResponse.of(branchService.findNearby(size, latitude, longitude)));
    }
}
