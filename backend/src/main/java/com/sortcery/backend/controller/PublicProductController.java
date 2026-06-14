package com.sortcery.backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sortcery.backend.dto.common.ApiResponse;
import com.sortcery.backend.dto.product.ProductPublicDTO;
import com.sortcery.backend.service.ProductSearchService;
import com.sortcery.backend.service.ProductService;

@RestController
@RequestMapping(path="/public/products")
public class PublicProductController {
    private final ProductService productService;
    private final ProductSearchService productSearchService;

    public PublicProductController(
        ProductService productService,
        ProductSearchService productSearchService
    ) {
        this.productService = productService;
        this.productSearchService = productSearchService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int size,

        @RequestParam(defaultValue = "") String search,
        @RequestParam(defaultValue = "popularity") String sort,

        @RequestParam(required = false) Long category,
        @RequestParam(required = false) Long brand,
        @RequestParam(required = false) Long branch,

        @RequestParam(required = false) Double latitude, 
        @RequestParam(required = false) Double longitude,
        @RequestParam(required = false) Double radius
    ) {
        return ResponseEntity.ok(
            ApiResponse.of(
                productSearchService.findPage(
                    page,
                    size,
                    sort,
                    search,
                    category,
                    brand,
                    branch,
                    latitude,
                    longitude,
                    radius
                )
            )
        );
    }

    @GetMapping(path="/top")
    public ResponseEntity<ApiResponse> findTopSellers(
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(required = false) Double latitude, 
        @RequestParam(required = false) Double longitude
    ) {
        return ResponseEntity.ok(ApiResponse.of(productSearchService.findTop(size, latitude, longitude, null)));
    }

    @GetMapping(path="/stats")
    public ResponseEntity<ApiResponse> stats() {
        return ResponseEntity.ok(ApiResponse.of(productService.stats()));
    }
}
