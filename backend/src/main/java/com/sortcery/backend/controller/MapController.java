package com.sortcery.backend.controller;

import com.sortcery.backend.service.MapService;
import com.sortcery.backend.dto.common.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path="/maps")
public class MapController {
    private final MapService mapService;

    MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping(path="/search")
    public ResponseEntity<ApiResponse> search(@RequestParam String query) {
        return ResponseEntity.ok(ApiResponse.of(mapService.search(query)));
    }
}
