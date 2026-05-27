package com.sortcery.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

    public List<ProductVariantResponseDTO> findAll() {
        return productVariantRepository.findAll()
            .stream()
            .map((product) -> new ProductVariantResponseDTO(product))
            .toList();
    }

    public ProductVariantResponseDTO findById(Long id) {
        return productVariantRepository.findById(id)
            .map((product) -> new ProductVariantResponseDTO(product))
            .orElseThrow(() -> new NotFoundException(Product.class, id));
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
