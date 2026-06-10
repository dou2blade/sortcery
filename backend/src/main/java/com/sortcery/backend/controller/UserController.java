package com.sortcery.backend.controller;

import com.sortcery.backend.service.UserService;
import com.sortcery.backend.dto.common.ApiResponse;
import com.sortcery.backend.dto.user.UserResponseDTO;
import com.sortcery.backend.model.User;
import com.sortcery.backend.dto.user.UserRequestDTO;
import com.sortcery.backend.validation.Create;
import com.sortcery.backend.validation.Update;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping(path="/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int size,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) User.Role role,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") 
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
        
        Page<UserResponseDTO> usersPage = userService.findPage(page, size, search, role, sort);
        return ResponseEntity.ok(ApiResponse.of(usersPage));
    }

    @GetMapping(path="/options")
    public ResponseEntity<ApiResponse> findOptions() {
        return ResponseEntity.ok(ApiResponse.of(userService.findOptions()));
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(
        @RequestBody @Validated(Create.class) UserRequestDTO request
    ) {
        UserResponseDTO savedUser = userService.save(request);
        return ResponseEntity.status(201).body(ApiResponse.of(savedUser));
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<ApiResponse> update(
        @PathVariable Long id,
        @RequestBody @Validated(Update.class) UserRequestDTO request
    ) {
        return ResponseEntity.ok(ApiResponse.of(userService.update(id, request)));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(userService.delete(id)));
    }

    @GetMapping(path="/stats")
    public ResponseEntity<ApiResponse> stats() {
        return ResponseEntity.ok(ApiResponse.of(userService.stats()));
    }
}
