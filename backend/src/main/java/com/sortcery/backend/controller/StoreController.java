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

import com.sortcery.backend.dto.common.ApiResponse;
import com.sortcery.backend.dto.store.StoreRequestDTO;
import com.sortcery.backend.dto.store.StoreResponseDTO;
import com.sortcery.backend.service.StoreService;
import com.sortcery.backend.validation.Create;
import com.sortcery.backend.validation.Update;

@RestController
@RequestMapping(path="/stores")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<StoreResponseDTO>> findAll() {
        List<StoreResponseDTO> stores = storeService.findAll();

        if (stores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(new ApiResponse<>(stores));
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<ApiResponse<StoreResponseDTO>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(storeService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StoreResponseDTO>> save(
        @RequestBody @Validated(Create.class) StoreRequestDTO request
    ) {
        StoreResponseDTO savedStore = storeService.save(request);
        return ResponseEntity.status(201).body(new ApiResponse<>(savedStore));
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity<ApiResponse<StoreResponseDTO>> update(
        @PathVariable Long id,
        @RequestBody @Validated(Update.class) StoreRequestDTO request
    ) {
        return ResponseEntity.ok(new ApiResponse<>(storeService.update(id, request)));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<ApiResponse<StoreResponseDTO>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(storeService.delete(id)));
    }
}
