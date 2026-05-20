package com.sortcery.backend.controller;

import com.sortcery.backend.service.UserService;
import com.sortcery.backend.dto.common.ApiResponse;
import com.sortcery.backend.dto.user.UserResponseDTO;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.validation.annotation.Validated;
import java.util.List;

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
        @RequestParam(required = false) String searchBy,
        @RequestParam(required = false) String search,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDir,
        @RequestParam(defaultValue = "false") boolean all
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") 
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
        
        if (all) {
            List<UserResponseDTO> users = userService.findAll(searchBy, search, sort);
            return ResponseEntity.ok(ApiResponse.of(users));
        } else {
            Page<UserResponseDTO> usersPage = userService.findPage(page, size, searchBy, search, sort);
            return ResponseEntity.ok(ApiResponse.of(usersPage));
        }
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

    @PatchMapping(path="/{id}")
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
}
