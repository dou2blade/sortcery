package com.sortcery.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sortcery.backend.dto.branchproductvariant.BranchProductVariantRequestDTO;
import com.sortcery.backend.dto.branchproductvariant.BranchProductVariantResponseDTO;
import com.sortcery.backend.exception.NotFoundException;
import com.sortcery.backend.model.Product;
import com.sortcery.backend.model.ProductVariant;
import com.sortcery.backend.model.Branch;
import com.sortcery.backend.model.BranchProductVariant;
import com.sortcery.backend.repository.ProductVariantRepository;
import com.sortcery.backend.repository.BranchProductVariantRepository;
import com.sortcery.backend.repository.BranchRepository;

@Service
public class BranchProductVariantService {
    private final BranchProductVariantRepository branchProductVariantRepository;
    private final ProductVariantRepository productVariantRepository;
    private final BranchRepository branchRepository;

    public BranchProductVariantService(
        BranchProductVariantRepository branchProductVariantRepository, 
        ProductVariantRepository productVariantRepository,
        BranchRepository branchRepository
    ) {
        this.branchProductVariantRepository = branchProductVariantRepository;
        this.productVariantRepository = productVariantRepository;
        this.branchRepository = branchRepository;
    }

    public Page<BranchProductVariantResponseDTO> findPage(
        int page,
        int size,
        String search,
        Long productId,
        Long branchId,
        Sort sort
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Specification<BranchProductVariant> spec = (root, query, cb) -> (
            cb.equal(
                root.get("branch").get("id"),
                branchId
            )
        );

        if (productId != null) {
            spec = spec.and((root, query, cb) -> (
                cb.equal(
                    root.get("productVariant").get("product").get("id"),
                    productId
                )
            ));
        }

        if (search != null && !search.isBlank()) {
            String term = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> (
                cb.or(
                    cb.like(cb.lower(root.get("productVariant").get("product").get("name")), term),
                    cb.like(cb.lower(root.get("productVariant").get("name")), term),
                    cb.like(cb.lower(root.get("sku")), term)
                )
            ));
        }

        return branchProductVariantRepository
            .findAll(spec, pageRequest)
            .map(BranchProductVariantResponseDTO::new);
    }

    public BranchProductVariantResponseDTO findById(Long id) {
        return branchProductVariantRepository.findById(id)
            .map((product) -> new BranchProductVariantResponseDTO(product))
            .orElseThrow(() -> new NotFoundException(Product.class, id));
    }

    public BranchProductVariantResponseDTO save(BranchProductVariantRequestDTO request) {
        Branch branch = branchRepository.findById(request.getBranchId())
            .orElseThrow(() -> new NotFoundException(Branch.class, request.getBranchId()));

        ProductVariant productVariant = productVariantRepository.findById(request.getProductVariantId())
            .orElseThrow(() -> new NotFoundException(ProductVariant.class, request.getProductVariantId()));

        BranchProductVariant saved = branchProductVariantRepository.save(new BranchProductVariant(
            branch,
            productVariant,
            request.getSku(),
            request.getPrice(),
            request.getQuantity()
        ));

        return new BranchProductVariantResponseDTO(saved);
    }

    public BranchProductVariantResponseDTO update(Long id, BranchProductVariantRequestDTO request) {
        BranchProductVariant existing = branchProductVariantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(BranchProductVariant.class, id));

        if (request.getBranchId() != null) {
            Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new NotFoundException(Branch.class, request.getBranchId()));
            existing.setBranch(branch);
        }

        if (request.getProductVariantId() != null) {
            ProductVariant productVariant = productVariantRepository.findById(request.getProductVariantId())
                .orElseThrow(() -> new NotFoundException(ProductVariant.class, request.getProductVariantId()));
            existing.setProductVariant(productVariant);
        }

        if (request.getSku() != null && !request.getSku().isBlank()) existing.setSku(request.getSku());
        if (request.getPrice() != null) existing.setPrice(request.getPrice());
        if (request.getQuantity() != null) existing.setQuantity(request.getQuantity());

        BranchProductVariant saved = branchProductVariantRepository.save(existing);

        return new BranchProductVariantResponseDTO(saved);
    }

    public BranchProductVariantResponseDTO delete(Long id) {
        BranchProductVariant deleted = branchProductVariantRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(BranchProductVariant.class, id));

        branchProductVariantRepository.deleteById(id);

        return new BranchProductVariantResponseDTO(deleted);
    }
}
