package com.sortcery.backend.service;

import com.sortcery.backend.model.Branch;
import com.sortcery.backend.model.Store;
import com.sortcery.backend.model.User;
import com.sortcery.backend.dto.branch.BranchRequestDTO;
import com.sortcery.backend.dto.branch.BranchResponseDTO;

import com.sortcery.backend.repository.StoreRepository;
import com.sortcery.backend.repository.UserRepository;

import com.sortcery.backend.repository.BranchRepository;
import com.sortcery.backend.exception.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BranchService {
    private final BranchRepository branchRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public BranchService(BranchRepository branchRepository, StoreRepository storeRepository, UserRepository userRepository) {
        this.branchRepository = branchRepository;
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
    }

    public List<BranchResponseDTO> findAll() {
        return branchRepository.findAll()
            .stream()
            .map((branch) -> new BranchResponseDTO(branch))
            .toList();
    }

    public BranchResponseDTO findById(Long id) {
        return branchRepository.findById(id)
            .map((branch) -> new BranchResponseDTO(branch))
            .orElseThrow(() -> new NotFoundException(Branch.class, id));
    }

    public BranchResponseDTO save(BranchRequestDTO request) {
        Store store = storeRepository.findById(request.getStoreId())
            .orElseThrow(() -> new NotFoundException(Store.class, request.getStoreId()));

        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new NotFoundException(User.class, request.getUserId()));

        Branch branch = new Branch();

        branch.setName(request.getName());
        branch.setStore(store);
        branch.setUser(user);
        
        Branch saved = branchRepository.save(branch);
        
        return new BranchResponseDTO(saved);
    }

    public BranchResponseDTO update(Long id, BranchRequestDTO request) {
        Branch existing = branchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Branch.class, id));

        if (request.getName() != null && !request.getName().isBlank()) existing.setName(request.getName());

 if (request.getStoreId() != null) {
            Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() ->
                    new NotFoundException(Store.class, request.getStoreId())
                );

            existing.setStore(store);
        }

        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                    new NotFoundException(User.class, request.getUserId())
                );

            existing.setUser(user);
        }
        Branch saved = branchRepository.save(existing);

        return new BranchResponseDTO(saved);
    }

    public BranchResponseDTO delete(Long id) {
        Branch deleted = branchRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Branch.class, id));

        branchRepository.deleteById(id);

        return new BranchResponseDTO(deleted);
    }
}
