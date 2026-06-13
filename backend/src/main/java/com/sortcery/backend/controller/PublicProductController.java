package com.sortcery.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sortcery.backend.dto.common.ApiResponse;
import com.sortcery.backend.service.ProductService;

@RestController
@RequestMapping(path="/public/products")
public class PublicProductController {
    private final ProductService productService;

    public PublicProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path="/top")
    public ResponseEntity<ApiResponse> findTopSellers(
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(required = false) Double latitude, 
        @RequestParam(required = false) Double longitude
    ) {
        return ResponseEntity.ok(ApiResponse.of(productService.findTop(size, latitude, longitude)));
    }

    @GetMapping(path="/stats")
    public ResponseEntity<ApiResponse> stats() {
        return ResponseEntity.ok(ApiResponse.of(productService.stats()));
    }
}
