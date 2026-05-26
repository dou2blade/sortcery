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

import com.sortcery.backend.dto.branchuser.BranchUserRequestDTO;
import com.sortcery.backend.dto.branchuser.BranchUserResponseDTO;
import com.sortcery.backend.dto.common.ApiResponse;
import com.sortcery.backend.service.BranchUserService;
import com.sortcery.backend.validation.Create;
import com.sortcery.backend.validation.Update;

@RestController
@RequestMapping(path="/branches")
public class BranchUserController {
    private final BranchUserService branchUserService;

    public BranchUserController(BranchUserService branchUserService) {
        this.branchUserService = branchUserService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll() {
        List<BranchUserResponseDTO> branchUsers = branchUserService.findAll();

        if (branchUsers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ApiResponse.of(branchUsers));
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<BranchUserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(branchUserService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(
        @RequestBody @Validated(Create.class) BranchUserRequestDTO request
    ) {
        BranchUserResponseDTO savedBranchUser = branchUserService.save(request);
        return ResponseEntity.status(201).body(ApiResponse.of(savedBranchUser));
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity<ApiResponse> update(
        @PathVariable Long id,
        @RequestBody @Validated(Update.class) BranchUserRequestDTO request
    ) {
        return ResponseEntity.ok(ApiResponse.of(branchUserService.update(id, request)));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(branchUserService.delete(id)));
    }
}