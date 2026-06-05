package com.sortcery.backend.service;

import com.sortcery.backend.dto.user.*;
import com.sortcery.backend.model.User;
import com.sortcery.backend.repository.UserRepository;

import com.sortcery.backend.exception.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder(10);

    public UserService(
        UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> findAll(
        String searchBy,
        String search,
        Sort sort
    ) {
        return userRepository
            .findAll(sort)
            .stream()
            .map(UserResponseDTO::new)
            .toList();
    }

    public Page<UserResponseDTO> findPage(
        int page,
        int size,
        String searchBy,
        String search,
        Sort sort
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return userRepository
            .findAll(pageRequest)
            .map(UserResponseDTO::new);
    }

    public UserResponseDTO findById(Long id) {
        return userRepository.findById(id)
                .map((user) -> new UserResponseDTO(user))
                .orElseThrow(() -> new NotFoundException(User.class, id));
    }

    public UserResponseDTO save(UserRequestDTO request) {
        User saved = new User(
                request.getFirstName(),
                request.getMiddleName(),
                request.getLastName(),
                request.getEmail(),
                bCryptEncoder.encode(request.getPassword()),
                request.getRole()
        );

        return new UserResponseDTO(userRepository.save(saved));
    }

    public UserResponseDTO update(Long id, UserRequestDTO request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(User.class, id));

        if (request.getFirstName() != null && !request.getFirstName().isBlank()) existing.setFirstName(request.getFirstName());
        if (request.getMiddleName() != null && !request.getMiddleName().isBlank()) existing.setMiddleName(request.getMiddleName());
        if (request.getLastName() != null && !request.getLastName().isBlank()) existing.setLastName(request.getLastName());
        if (request.getEmail() != null && !request.getEmail().isBlank()) existing.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) existing.setPassword(bCryptEncoder.encode(request.getPassword()));
        if (request.getRole() != null) existing.setRole(request.getRole()); 

        User saved = userRepository.save(existing);

        return new UserResponseDTO(saved);
    }

    public UserResponseDTO delete(Long id) {
        User deleted = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(User.class, id));

        userRepository.deleteById(id);

        return new UserResponseDTO(deleted);
    }
}
