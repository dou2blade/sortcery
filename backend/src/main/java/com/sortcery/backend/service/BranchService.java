package com.sortcery.backend.service;

import com.sortcery.backend.model.Branch;
import com.sortcery.backend.model.BranchProductVariant;
import com.sortcery.backend.model.Product;
import com.sortcery.backend.model.ProductVariant;
import com.sortcery.backend.model.Store;
import com.sortcery.backend.model.User;
import com.sortcery.backend.model.UserBranch;
import com.sortcery.backend.algorithms.Haversine;
import com.sortcery.backend.algorithms.MergeSort;
import com.sortcery.backend.dto.branch.BranchPublicDTO;
import com.sortcery.backend.dto.branch.BranchRequestDTO;
import com.sortcery.backend.dto.branch.BranchResponseDTO;
import com.sortcery.backend.dto.branch.BranchStatsDTO;
import com.sortcery.backend.repository.StoreRepository;
import com.sortcery.backend.repository.UserBranchRepository;
import com.sortcery.backend.repository.UserRepository;


import com.sortcery.backend.repository.BranchRepository;
import com.sortcery.backend.repository.InventoryMovementRepository;
import com.sortcery.backend.exception.NotFoundException;
import com.sortcery.backend.exception.ValidationException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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
    private final InventoryMovementRepository inventoryMovementRepository;

    public BranchService(
        BranchRepository branchRepository,
		StoreRepository storeRepository,
		UserRepository userRepository,
        UserBranchRepository userBranchRepository,
        InventoryMovementRepository inventoryMovementRepository
    ) {
        this.branchRepository = branchRepository;
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
        this.userBranchRepository = userBranchRepository;
        this.inventoryMovementRepository = inventoryMovementRepository;
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

    private void syncUsers(Branch branch, Set<Long> userIds) {
        List<User> targetUsers = userRepository.findAllById(userIds);
        if (targetUsers.size() != userIds.size()) {
            throw new ValidationException(Map.of(
                "managerIds", "One or more users do not exist",
                "retailerIds", "One or more users do not exist"
            ));
        }

        List<UserBranch> existingRelations = userBranchRepository.findByBranchId(branch.getId());
        Set<Long> existingUserIds = existingRelations.stream()
            .map((rel) -> rel.getUser().getId())
            .collect(Collectors.toSet());

        List<UserBranch> additions = targetUsers.stream()
            .filter((user) -> !existingUserIds.contains(user.getId()))
            .map((user) -> new UserBranch(user, branch))
            .toList();
        List<UserBranch> removals = existingRelations.stream()
            .filter((rel) -> !userIds.contains(rel.getUser().getId()))
            .toList();

        userBranchRepository.saveAll(additions);
        userBranchRepository.deleteAll(removals);
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

        if (!userIds.isEmpty()) syncUsers(existing, userIds);

        Branch saved = branchRepository.save(existing);

        return new BranchResponseDTO(saved);
    }

    public BranchResponseDTO updateUsers(Long id, BranchRequestDTO request) {
        Branch existing = branchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Branch.class, id));

        Set<Long> userIds = new HashSet<>(request.getManagerIds());
        userIds.addAll(request.getRetailerIds());

        if (!userIds.isEmpty()) syncUsers(existing, userIds);

        Branch saved = branchRepository.save(existing);

        return new BranchResponseDTO(saved);
    }

    public BranchResponseDTO delete(Long id) {
        Branch deleted = branchRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Branch.class, id));

        branchRepository.deleteById(id);

        return new BranchResponseDTO(deleted);
    }

    public BranchStatsDTO stats(Long id) {
        Branch branch = branchRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Branch.class, id));

        Set<Product> products = branch.getBranchProductVariants()
            .stream()
            .map(BranchProductVariant::getProductVariant)
            .map(ProductVariant::getProduct)
            .collect(Collectors.toSet());

        LocalDateTime now = LocalDateTime.now();

        long weeklySales = inventoryMovementRepository.sumSalesSince(
            id,
            now.minusWeeks(1)
        );

        long monthlySales = inventoryMovementRepository.sumSalesSince(
            id,
            now.minusMonths(1)
        );

        return new BranchStatsDTO(
            branch.getManagers().size(),
            branch.getRetailers().size(),
            products.size(),
            weeklySales,
            monthlySales
        );
    }

    public List<BranchPublicDTO> findNearby(int size, Double latitude, Double longitude) {
        List<BranchPublicDTO> branches = branchRepository.findAll()
            .stream()
            .map((branch) -> new BranchPublicDTO(
                branch,
                Haversine.distance(
                    branch.getLatitude(),
                    branch.getLongitude(),
                    latitude,
                    longitude
                )
            )).toList();

        return MergeSort.sort(
            branches,
            Comparator.comparingDouble(
                BranchPublicDTO::getDistance
            )).subList(0, Math.min(size, branches.size()));
    }
}
