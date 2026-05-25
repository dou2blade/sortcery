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

import com.sortcery.backend.dto.branchstock.BranchStockRequestDTO;
import com.sortcery.backend.dto.branchstock.BranchStockResponseDTO;
import com.sortcery.backend.dto.common.ApiResponse;
import com.sortcery.backend.service.BranchStockService;
import com.sortcery.backend.validation.Create;
import com.sortcery.backend.validation.Update;

@RestController
@RequestMapping(path="/branches")
public class BranchStockController {
    private final BranchStockService branchStockService;

    public BranchStockController(BranchStockService branchStockService) {
        this.branchStockService = branchStockService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll() {
        List<BranchStockResponseDTO> branchStocks = branchStockService.findAll();

        if (branchStocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ApiResponse.of(branchStocks));
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<BranchStockResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(branchStockService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(
        @RequestBody @Validated(Create.class) BranchStockRequestDTO request
    ) {
        BranchStockResponseDTO savedBranchStock = branchStockService.save(request);
        return ResponseEntity.status(201).body(ApiResponse.of(savedBranchStock));
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity<ApiResponse> update(
        @PathVariable Long id,
        @RequestBody @Validated(Update.class) BranchStockRequestDTO request
    ) {
        return ResponseEntity.ok(ApiResponse.of(branchStockService.update(id, request)));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(branchStockService.delete(id)));
    }
}