package com.sortcery.backend.service;

import com.sortcery.backend.model.Branch;
import com.sortcery.backend.model.Store;
import com.sortcery.backend.model.User;
import com.sortcery.backend.model.UserBranch;
import com.sortcery.backend.dto.branch.BranchRequestDTO;
import com.sortcery.backend.dto.branch.BranchResponseDTO;

import com.sortcery.backend.repository.StoreRepository;
import com.sortcery.backend.repository.UserBranchRepository;
import com.sortcery.backend.repository.UserRepository;


import com.sortcery.backend.repository.BranchRepository;
import com.sortcery.backend.exception.NotFoundException;
import com.sortcery.backend.exception.ValidationException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BranchService {
    private final BranchRepository branchRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final UserBranchRepository userBranchRepository;

    public BranchService(
        BranchRepository branchRepository,
		StoreRepository storeRepository,
		UserRepository userRepository,
        UserBranchRepository userBranchRepository
    ) {
        this.branchRepository = branchRepository;
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
        this.userBranchRepository = userBranchRepository;
    }

    public Page<BranchResponseDTO> findPage(
        int page,
        int size,
        String search,
        Long store,
        Sort sort
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Specification<Branch> spec = (root, query, cb) -> cb.conjunction();

        if (search != null && !search.isBlank()) {
            String term = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> (
                cb.or(
                    cb.like(cb.lower(root.get("name")), term),
                    cb.like(cb.lower(root.get("address")), term)
                )
            ));
        }

        if (store != null) {
            spec = spec.and((root, query, cb) -> (
                cb.equal(
                    root.get("store").get("id"), 
                    store
                )
            ));
        }

        return branchRepository
            .findAll(spec, pageRequest)
            .map(BranchResponseDTO::new);
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

    @Transactional
    public BranchResponseDTO save(BranchRequestDTO request) {
        System.out.println(request.toString());
        Store store = storeRepository.findById(request.getStoreId())
            .orElseThrow(() -> new NotFoundException(Store.class, request.getStoreId()));

        Branch saved = branchRepository.save(new Branch(
            store,
            request.getName(),
            request.getAddress(),
            request.getLatitude(),
            request.getLongitude()
        ));

        Set<Long> userIds = new HashSet<>(request.getManagerIds());
        userIds.addAll(request.getRetailerIds());
        List<User> targetUsers = userRepository.findAllById(userIds);

        if (targetUsers.size() != userIds.size()) {
            throw new NotFoundException("One or more users do not exist");
        }

        List<UserBranch> userBranches = new ArrayList<>();
        for (User user : targetUsers) {
            if (!user.getRole().isAssignableToBranch()) {
                throw new ValidationException(Map.of(
                    "managerIds", "One or more users have invalid roles",
                    "retailerIds", "One or more users have invalid roles"
                ));
            }

            userBranches.add(new UserBranch(user, saved));
        }

        userBranchRepository.saveAll(userBranches);
        
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

        if (request.getAddress() != null && !request.getAddress().isBlank()) existing.setAddress(request.getAddress());
        if (request.getLatitude() != null) existing.setLatitude(request.getLatitude());
        if (request.getLongitude() != null) existing.setLongitude(request.getLongitude());

        Set<Long> userIds = new HashSet<>(request.getManagerIds());
        userIds.addAll(request.getRetailerIds());

        if (!userIds.isEmpty()) {
            List<User> targetUsers = userRepository.findAllById(userIds);
            if (targetUsers.size() != userIds.size()) {
                throw new ValidationException(Map.of(
                    "managerIds", "One or more users do not exist",
                    "retailerIds", "One or more users do not exist"
                ));
            }

            List<UserBranch> existingRelations = userBranchRepository.findByBranchId(id);
            Set<Long> existingUserIds = existingRelations.stream()
                .map((rel) -> rel.getUser().getId())
                .collect(Collectors.toSet());

            List<UserBranch> additions = targetUsers.stream()
                .filter((user) -> !existingUserIds.contains(user.getId()))
                .map((user) -> new UserBranch(user, existing))
                .toList();
            List<UserBranch> removals = existingRelations.stream()
                .filter((rel) -> !userIds.contains(rel.getUser().getId()))
                .toList();

            userBranchRepository.saveAll(additions);
            userBranchRepository.deleteAll(removals);
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
