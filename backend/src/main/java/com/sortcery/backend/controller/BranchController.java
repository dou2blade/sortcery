package com.sortcery.backend.controller;

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

import com.sortcery.backend.dto.branch.BranchRequestDTO;
import com.sortcery.backend.dto.branch.BranchResponseDTO;
import com.sortcery.backend.dto.common.ApiResponse;
import com.sortcery.backend.service.BranchService;
import com.sortcery.backend.validation.Create;
import com.sortcery.backend.validation.Update;

@RestController
@RequestMapping(path="/branches")
public class BranchController {
    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int size,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) Long store,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") 
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
        
        Page<BranchResponseDTO> branchesPage = branchService.findPage(page, size, search, store, sort);
        return ResponseEntity.ok(ApiResponse.of(branchesPage));
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(branchService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(
        @RequestBody @Validated(Create.class) BranchRequestDTO request
    ) {
        BranchResponseDTO savedBranch = branchService.save(request);
        return ResponseEntity.status(201).body(ApiResponse.of(savedBranch));
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<ApiResponse> update(
        @PathVariable Long id,
        @RequestBody @Validated(Update.class) BranchRequestDTO request
    ) {
        return ResponseEntity.ok(ApiResponse.of(branchService.update(id, request)));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(branchService.delete(id)));
    }
}
