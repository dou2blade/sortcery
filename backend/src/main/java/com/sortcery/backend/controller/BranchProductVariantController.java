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

import com.sortcery.backend.dto.branchproductvariant.BranchProductVariantRequestDTO;
import com.sortcery.backend.dto.branchproductvariant.BranchProductVariantResponseDTO;
import com.sortcery.backend.service.BranchProductVariantService;
import com.sortcery.backend.dto.common.ApiResponse;
import com.sortcery.backend.validation.Create;
import com.sortcery.backend.validation.Update;

@RestController
@RequestMapping(path="/branch-product-variants")
public class BranchProductVariantController {
    private final BranchProductVariantService productVariantService;

    public BranchProductVariantController(BranchProductVariantService productVariantService) {
        this.productVariantService = productVariantService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll() {
        List<BranchProductVariantResponseDTO> productVariants = productVariantService.findAll();

        if (productVariants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ApiResponse.of(productVariants));
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<BranchProductVariantResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productVariantService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(
        @RequestBody @Validated(Create.class) BranchProductVariantRequestDTO request
    ) {
        BranchProductVariantResponseDTO savedBranchProductVariant = productVariantService.save(request);
        return ResponseEntity.status(201).body(ApiResponse.of(savedBranchProductVariant));
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity<ApiResponse> update(
        @PathVariable Long id,
        @RequestBody @Validated(Update.class) BranchProductVariantRequestDTO request
    ) {
        return ResponseEntity.ok(ApiResponse.of(productVariantService.update(id, request)));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(productVariantService.delete(id)));
    }
}
