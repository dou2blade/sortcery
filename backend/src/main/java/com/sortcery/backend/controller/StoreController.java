package com.sortcery.backend.controller;

import com.sortcery.backend.service.StoreService;
import com.sortcery.backend.dto.common.PaginatedResponse;
import com.sortcery.backend.dto.store.StoreResponseDTO;
import com.sortcery.backend.dto.store.UserRequestDTO;
import com.sortcery.backend.validation.Create;
import com.sortcery.backend.validation.Update;

import com.sortcery.backend.validation.Create;
import com.sortcery.backend.validation.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@RestController
@RequestMapping(path="/stores")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public ResponseEntity<List<StoreResponseDTO>> findAll() {
        List<StoreResponseDTO> stores = storeService.findAll();

        if (stores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(stores);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<StoreResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<StoreResponseDTO> save(
        @RequestBody @Validated(Create.class) StoreRequestDTO request
    ) {
        StoreResponseDTO savedStore = storeService.save(request);
        return ResponseEntity.status(201).body(savedStore);
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity<StoreResponseDTO> update(
        @PathVariable Long id,
        @RequestBody @Validated(Update.class) StoreRequestDTO request
    ) {
        return ResponseEntity.ok(storeService.update(id, request));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<StoreResponseDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.delete(id));
    }
}