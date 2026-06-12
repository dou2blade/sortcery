package com.sortcery.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sortcery.backend.dto.productvariant.ProductVariantOptionDTO;
import com.sortcery.backend.dto.productvariant.ProductVariantRequestDTO;
import com.sortcery.backend.dto.productvariant.ProductVariantResponseDTO;
import com.sortcery.backend.exception.NotFoundException;
import com.sortcery.backend.model.Product;
import com.sortcery.backend.model.ProductVariant;
import com.sortcery.backend.repository.ProductRepository;
import com.sortcery.backend.repository.ProductVariantRepository;

@Service
public class ProductVariantService {
    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;

    public ProductVariantService(
        ProductVariantRepository productVariantRepository, 
        ProductRepository productRepository
    ) {
        this.productVariantRepository = productVariantRepository;
        this.productRepository = productRepository;
    }

    public Page<ProductVariantResponseDTO> findPage(
        int page,
        int size,
        String search,
        Long productId,
        Sort sort
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Specification<ProductVariant> spec = (root, query, cb) -> cb.conjunction();

        if (search != null && !search.isBlank()) {
            String term = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> (
                cb.like(cb.lower(root.get("name")), term)
            ));
        }

        if (productId != null) {
            spec = spec.and((root, query, cb) -> (
                cb.equal(
                    root.get("product").get("id"),
                    productId)
                )
            );
        }

        return productVariantRepository
            .findAll(spec, pageRequest)
            .map(ProductVariantResponseDTO::new);
    }

    public List<ProductVariantOptionDTO> findOptions() {
        return productVariantRepository.findAll()
            .stream()
            .map(ProductVariantOptionDTO::new)
            .toList();
    }

    public ProductVariantResponseDTO findById(Long id) {
        return productVariantRepository.findById(id)
            .map(ProductVariantResponseDTO::new)
            .orElseThrow(() -> new NotFoundException(Product.class, id));
    }

    public List<ProductVariantResponseDTO> findByProductId(Long productId) {
        return productVariantRepository.findByProductId(productId)
            .stream()
            .map(ProductVariantResponseDTO::new)
            .toList();
    }

    public ProductVariantResponseDTO save(ProductVariantRequestDTO request) {

        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new NotFoundException(Product.class, request.getProductId()));

        ProductVariant saved = productVariantRepository.save(new ProductVariant(
            product,
            request.getName(),
            request.getImageUrl()
        ));

        return new ProductVariantResponseDTO(saved);
    }

    public ProductVariantResponseDTO update(Long id, ProductVariantRequestDTO request) {
        ProductVariant existing = productVariantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ProductVariant.class, id));

        if (request.getName() != null && !request.getName().isBlank()) existing.setName(request.getName());
        if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) existing.setImageUrl(request.getImageUrl());
        if (request.getProductId() != null) {
            Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException(Product.class, request.getProductId()));
            existing.setProduct(product);
        }

        ProductVariant saved = productVariantRepository.save(existing);

        return new ProductVariantResponseDTO(saved);
    }

    public ProductVariantResponseDTO delete(Long id) {
        ProductVariant deleted = productVariantRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ProductVariant.class, id));

        productVariantRepository.deleteById(id);

        return new ProductVariantResponseDTO(deleted);
    }
}
