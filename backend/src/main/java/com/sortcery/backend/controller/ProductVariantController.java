package com.sortcery.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sortcery.backend.dto.productvariant.ProductVariantRequestDTO;
import com.sortcery.backend.dto.productvariant.ProductVariantResponseDTO;
import com.sortcery.backend.service.ProductVariantService;
import com.sortcery.backend.dto.common.ApiResponse;
import com.sortcery.backend.validation.Create;
import com.sortcery.backend.validation.Update;

@RestController
@RequestMapping(path="/product-variants")
public class ProductVariantController {
    private final ProductVariantService productVariantService;

    public ProductVariantController(ProductVariantService productVariantService) {
        this.productVariantService = productVariantService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll() {
        List<ProductVariantResponseDTO> productVariants = productVariantService.findAll();

        if (productVariants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ApiResponse.of(productVariants));
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<ProductVariantResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productVariantService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(
        @RequestBody @Validated(Create.class) ProductVariantRequestDTO request
    ) {
        ProductVariantResponseDTO savedProductVariant = productVariantService.save(request);
        return ResponseEntity.status(201).body(ApiResponse.of(savedProductVariant));
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity<ApiResponse> update(
        @PathVariable Long id,
        @RequestBody @Validated(Update.class) ProductVariantRequestDTO request
    ) {
        return ResponseEntity.ok(ApiResponse.of(productVariantService.update(id, request)));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(productVariantService.delete(id)));
    }
}
