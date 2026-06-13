package com.sortcery.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sortcery.backend.dto.common.ApiResponse;
import com.sortcery.backend.service.BranchService;

@RestController
@RequestMapping(path="/public/branches")
public class PublicBranchController {
    private final BranchService branchService;

    public PublicBranchController(BranchService branchService) {
        this.branchService = branchService;
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
