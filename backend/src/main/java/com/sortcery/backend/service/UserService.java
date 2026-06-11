package com.sortcery.backend.service;

import com.sortcery.backend.model.User;
import com.sortcery.backend.repository.UserRepository;
import com.sortcery.backend.dto.user.UserOptionDTO;
import com.sortcery.backend.dto.user.UserRequestDTO;
import com.sortcery.backend.dto.user.UserResponseDTO;
import com.sortcery.backend.dto.user.UserStatsDTO;
import com.sortcery.backend.exception.NotFoundException;
import com.sortcery.backend.exception.ValidationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder(10);

    public UserService(
        UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public Page<UserResponseDTO> findPage(
        int page,
        int size,
        String search,
        User.Role role,
        Sort sort
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Specification<User> spec = (root, query, cb) -> cb.conjunction();

        if (role != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("role"), role));

        if (search != null && !search.isBlank()) {
            String term = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> (
                cb.or(
                    cb.like(cb.lower(root.get("email")), term),
                    cb.like(cb.lower(root.get("firstName")), term),
                    cb.like(cb.lower(root.get("middleName")), term),
                    cb.like(cb.lower(root.get("lastName")), term)
                )
            ));
        }

        return userRepository
            .findAll(spec, pageRequest)
            .map(UserResponseDTO::new);
    }

    public Map<User.Role, List<UserOptionDTO>> findOptions(String search) {
        Specification<User> spec = (root, query, cb) -> cb.conjunction();
        if (search != null && !search.isBlank()) {
            String term = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("email")), term));
        }

        List<User> users = userRepository.findAll(spec);

        return Map.of(
            User.Role.MANAGER, users.stream()
                .filter((user) -> user.getRole() == User.Role.MANAGER)
                .map(UserOptionDTO::new)
                .toList(),
            User.Role.RETAILER, users.stream()
                .filter((user) -> user.getRole() == User.Role.RETAILER)
                .map(UserOptionDTO::new)
                .toList()
        );
    }

    public UserResponseDTO findById(Long id) {
        return userRepository.findById(id)
                .map((user) -> new UserResponseDTO(user))
                .orElseThrow(() -> new NotFoundException(User.class, id));
    }

    public UserResponseDTO save(UserRequestDTO request) {
        try {
            User saved = new User(
                    request.getFirstName(),
                    request.getMiddleName(),
                    request.getLastName(),
                    request.getEmail(),
                    bCryptEncoder.encode(request.getPassword()),
                    request.getRole()
            );

            return new UserResponseDTO(userRepository.save(saved));
        } catch (DataIntegrityViolationException exception) {
            throw new ValidationException(Map.of("email", "Email already exists"));
        }
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

    public UserStatsDTO stats() {
        List<Object[]> raw = userRepository.countByRole();
        Map<User.Role, Long> byRole = new EnumMap<>(User.Role.class);

        for (Object[] row : raw) {
            User.Role role = (User.Role) row[0];
            Long count = (Long) row[1];
            byRole.put(role, count);
        }

        long total = byRole.values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();

        return new UserStatsDTO(total, byRole);
    }
}
