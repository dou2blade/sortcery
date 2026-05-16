package com.sortcery.backend.controller;

import com.sortcery.backend.service.UserService;
import com.sortcery.backend.dto.common.PaginatedResponse;
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
    public ResponseEntity<PaginatedResponse<UserResponseDTO>> findAll(
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
            return ResponseEntity.ok(new PaginatedResponse<>(users));
        } else {
            Page<UserResponseDTO> usersPage = userService.findPage(page, size, searchBy, search, sort);
            return ResponseEntity.ok(new PaginatedResponse<>(usersPage));
        }
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> save(
            @RequestBody @Validated(Create.class) UserRequestDTO request
    ) {
        UserResponseDTO savedUser = userService.save(request);
        return ResponseEntity.status(201).body(savedUser);
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Validated(Update.class) UserRequestDTO request
    ) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<UserResponseDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userService.delete(id));
    }
}
