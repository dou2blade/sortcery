package com.sortcery.backend.service;

import com.sortcery.backend.model.BranchUser;
import com.sortcery.backend.model.Branch;
import com.sortcery.backend.model.User;
import com.sortcery.backend.dto.branchuser.BranchUserRequestDTO;
import com.sortcery.backend.dto.branchuser.BranchUserResponseDTO;

import com.sortcery.backend.repository.BranchUserRepository;
import com.sortcery.backend.repository.BranchRepository;
import com.sortcery.backend.repository.UserRepository;

import com.sortcery.backend.exception.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BranchUserService {
    private final BranchUserRepository branchUserRepository;
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;

    public BranchUserService(BranchUserRepository branchUserRepository, BranchRepository branchRepository, UserRepository userRepository) {
        this.branchUserRepository = branchUserRepository;
        this.branchRepository = branchRepository;
        this.userRepository = userRepository;
    }

    public List<BranchUserResponseDTO> findAll() {
        return branchUserRepository.findAll()
            .stream()
            .map((branchUser) -> new BranchUserResponseDTO(branchUser))
            .toList();
    }

    public BranchUserResponseDTO findById(Long id) {
        return branchUserRepository.findById(id)
            .map((branchUser) -> new BranchUserResponseDTO(branchUser))
            .orElseThrow(() -> new NotFoundException(BranchUser.class, id));
    }

    public BranchUserResponseDTO save(BranchUserRequestDTO request) {
        Branch branch = branchRepository.findById(request.getBranchId())
            .orElseThrow(() -> new NotFoundException(Branch.class, request.getBranchId()));

        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new NotFoundException(User.class, request.getUserId()));

        BranchUser branchUser = new BranchUser();

        branchUser.setBranch(branch);
        branchUser.setUser(user);

        BranchUser saved = branchUserRepository.save(branchUser);

        return new BranchUserResponseDTO(saved);
    }

    public BranchUserResponseDTO update(Long id, BranchUserRequestDTO request) {
        BranchUser existing = branchUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(BranchUser.class, id));

 if (request.getBranchId() != null) {
            Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() ->
                    new NotFoundException(Branch.class, request.getBranchId())
                );

            existing.setBranch(branch);
        }

        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                    new NotFoundException(User.class, request.getUserId())
                );

            existing.setUser(user);
        }
        BranchUser saved = branchUserRepository.save(existing);

        return new BranchUserResponseDTO(saved);
    }

    public BranchUserResponseDTO delete(Long id) {
        BranchUser deleted = branchUserRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(BranchUser.class, id));

        branchUserRepository.deleteById(id);

        return new BranchUserResponseDTO(deleted);
    }
}