package com.sortcery.backend.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sortcery.backend.dto.common.ApiResponse;
import com.sortcery.backend.dto.inventorymovement.InventoryMovementRequestDTO;
import com.sortcery.backend.dto.inventorymovement.InventoryMovementResponseDTO;
import com.sortcery.backend.service.InventoryMovementService;
import com.sortcery.backend.validation.Create;

@RestController
@RequestMapping(path="/inventory-movements")
public class InventoryMovementController {
    private final InventoryMovementService inventoryMovementService;

    public InventoryMovementController(InventoryMovementService inventoryMovementService) {
        this.inventoryMovementService = inventoryMovementService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int size,
        @RequestParam(required = false) String search,
        @RequestParam(required = true) Long branch,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") 
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
        
        Page<InventoryMovementResponseDTO> InventoryMovementPage = inventoryMovementService.findPage(page, size, search, branch, sort);
        return ResponseEntity.ok(ApiResponse.of(InventoryMovementPage));
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<ApiResponse> findById(
        @PathVariable Long id,
        @RequestParam(required = true) Long branch
    ) {
        return ResponseEntity.ok(ApiResponse.of(inventoryMovementService.findById(id, branch)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(
        @RequestBody @Validated(Create.class) InventoryMovementRequestDTO request
    ) {
        InventoryMovementResponseDTO savedInventoryMovement = inventoryMovementService.save(request);
        return ResponseEntity.status(201).body(ApiResponse.of(savedInventoryMovement));
    }
}
