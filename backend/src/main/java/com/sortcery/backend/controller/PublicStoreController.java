package com.sortcery.backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sortcery.backend.dto.common.ApiResponse;
import com.sortcery.backend.dto.store.StoreResponseDTO;
import com.sortcery.backend.service.StoreService;

@RestController
@RequestMapping(path="/public/stores")
public class PublicStoreController {
    private final StoreService StoreService;

    public PublicStoreController(StoreService StoreService) {
        this.StoreService = StoreService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int size,
        @RequestParam(required = false) String search,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") 
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
        
        Page<StoreResponseDTO> storesPage = StoreService.findPage(page, size, search, sort);
        return ResponseEntity.ok(ApiResponse.of(storesPage));
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(StoreService.findById(id)));
    }

}
