package com.sortcery.backend.service;

import com.sortcery.backend.model.BranchProductVariant;
import com.sortcery.backend.model.InventoryMovement;
import com.sortcery.backend.model.User;
import com.sortcery.backend.dto.inventorymovement.InventoryMovementRequestDTO;
import com.sortcery.backend.dto.inventorymovement.InventoryMovementResponseDTO;
import com.sortcery.backend.repository.BranchProductVariantRepository;
import com.sortcery.backend.repository.InventoryMovementRepository;
import com.sortcery.backend.repository.UserRepository;

import jakarta.validation.ValidationException;

import com.sortcery.backend.exception.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class InventoryMovementService {
    private final InventoryMovementRepository inventoryMovementRepository;
    private final BranchProductVariantRepository branchProductVariantRepository;
    private final UserRepository userRepository;

    public InventoryMovementService(
        InventoryMovementRepository inventoryMovementRepository,
        BranchProductVariantRepository branchProductVariantRepository,
        UserRepository userRepository
    ) {
        this.inventoryMovementRepository = inventoryMovementRepository;
        this.branchProductVariantRepository = branchProductVariantRepository;
        this.userRepository = userRepository;
    }

    public Page<InventoryMovementResponseDTO> findPage(
        int page,
        int size,
        String search,
        Long branchId,
        Sort sort
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Specification<InventoryMovement> spec = (root, query, cb) -> (
            cb.equal(
                root.get("branchProductVariant").get("branch").get("id"),
                branchId
            )
        );

        if (search != null && !search.isBlank()) {
            String term = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> (
                cb.or(
                    cb.like(cb.lower(root.get("branchProductVariant").get("productVariant").get("product").get("name")), term),
                    cb.like(cb.lower(root.get("branchProductVariant").get("productVariant").get("name")), term),
                    cb.like(cb.lower(root.get("branchProductVariant").get("sku")), term)
                )
            ));
        }

        return inventoryMovementRepository
            .findAll(spec, pageRequest)
            .map(InventoryMovementResponseDTO::new);
    }


    public InventoryMovementResponseDTO findById(Long id, Long branchId) {
        return inventoryMovementRepository.findById(id)
            .map((inventoryMovement) -> new InventoryMovementResponseDTO(inventoryMovement))
            .orElseThrow(() -> new NotFoundException(InventoryMovement.class, id));
    }

    public InventoryMovementResponseDTO save(InventoryMovementRequestDTO request) {
        boolean valid =
            request.getType().isPositive()
                ? request.getQuantityChange() >= 0
                : request.getQuantityChange() <= 0;

        if (!valid) {
            throw new ValidationException(
                String.format(
                    "%s movement must have a %s value",
                    request.getType(),
                    request.getType().isPositive() ? "positive" : "negative"
                )
            );
        }

        BranchProductVariant branchProductVariant = branchProductVariantRepository.findById(request.getBranchProductVariantId())
            .orElseThrow(() -> new NotFoundException(BranchProductVariant.class, request.getBranchProductVariantId()));

        User createdBy = userRepository.findById(request.getCreatedById())
            .orElseThrow(() -> new NotFoundException(User.class, request.getCreatedById()));

        Integer newQuantity = branchProductVariant.getQuantity() + request.getQuantityChange();

        if (newQuantity < 0) {
            throw new ValidationException("Inventory cannot become negative");
        }

        branchProductVariant.setQuantity(newQuantity);

        InventoryMovement saved = inventoryMovementRepository.save(new InventoryMovement(
            branchProductVariant,
            request.getType(),
            request.getQuantityChange(),
            newQuantity,
            request.getNotes(),
            createdBy
        ));
        return new InventoryMovementResponseDTO(saved);
    }
}
