package com.sortcery.backend.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final BranchProductVariantService branchProductVariantService;

    public BranchProductVariantController(BranchProductVariantService branchProductVariantService) {
        this.branchProductVariantService = branchProductVariantService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int size,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) Long product,
        @RequestParam(required = true) Long branch,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") 
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
        
        Page<BranchProductVariantResponseDTO> branchProductVariantsPage = branchProductVariantService.findPage(page, size, search, product, branch, sort);
        return ResponseEntity.ok(ApiResponse.of(branchProductVariantsPage));
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<ApiResponse> findById(
        @PathVariable Long id,
        @RequestParam(required = true) Long branch
    ) {
        return ResponseEntity.ok(ApiResponse.of(branchProductVariantService.findById(id, branch)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(
        @RequestBody @Validated(Create.class) BranchProductVariantRequestDTO request,
        @RequestParam(required = true) Long branch
    ) {
        BranchProductVariantResponseDTO savedBranchProductVariant = branchProductVariantService.save(branch, request);
        return ResponseEntity.status(201).body(ApiResponse.of(savedBranchProductVariant));
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<ApiResponse> update(
        @PathVariable Long id,
        @RequestBody @Validated(Update.class) BranchProductVariantRequestDTO request,
        @RequestParam(required = true) Long branch
    ) {
        return ResponseEntity.ok(ApiResponse.of(branchProductVariantService.update(id, branch, request)));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<ApiResponse> delete(
        @PathVariable Long id,
        @RequestParam(required = true) Long branch
    ) {
        return ResponseEntity.ok(ApiResponse.of(branchProductVariantService.delete(id, branch)));
    }
}
