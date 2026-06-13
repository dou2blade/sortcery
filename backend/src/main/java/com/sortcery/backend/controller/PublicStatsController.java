package com.sortcery.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sortcery.backend.dto.common.ApiResponse;
import com.sortcery.backend.service.PublicStatsService;

@RestController
@RequestMapping(path="/public/stats")
public class PublicStatsController {
    private final PublicStatsService publicStatsService;

    public PublicStatsController(PublicStatsService publicStatsService) {
        this.publicStatsService = publicStatsService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> stats() {
        return ResponseEntity.ok(ApiResponse.of(publicStatsService.stats()));
    }
}
