package com.sortcery.backend.service;

import com.sortcery.backend.model.ProductCategory;
import com.sortcery.backend.dto.productcategory.ProductCategoryRequestDTO;
import com.sortcery.backend.dto.productcategory.ProductCategoryResponseDTO;
import com.sortcery.backend.repository.ProductCategoryRepository;
import com.sortcery.backend.exception.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<ProductCategoryResponseDTO> findAll() {
        return productCategoryRepository.findAll()
            .stream()
            .map((productCategory) -> new ProductCategoryResponseDTO(productCategory))
            .toList();
    }

    public ProductCategoryResponseDTO findById(Long id) {
        return productCategoryRepository.findById(id)
            .map((productCategory) -> new ProductCategoryResponseDTO(productCategory))
            .orElseThrow(() -> new NotFoundException(ProductCategory.class, id));
    }

    public ProductCategoryResponseDTO save(ProductCategoryRequestDTO request) {
        ProductCategory saved = productCategoryRepository.save(new ProductCategory(
            request.getName()
        ));
        return new ProductCategoryResponseDTO(saved);
    }

    public ProductCategoryResponseDTO update(Long id, ProductCategoryRequestDTO request) {
        ProductCategory existing = productCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ProductCategory.class, id));

        if (request.getName() != null && !request.getName().isBlank()) existing.setName(request.getName());

        ProductCategory saved = productCategoryRepository.save(existing);

        return new ProductCategoryResponseDTO(saved);
    }

    public ProductCategoryResponseDTO delete(Long id) {
        ProductCategory deleted = productCategoryRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ProductCategory.class, id));

        productCategoryRepository.deleteById(id);

        return new ProductCategoryResponseDTO(deleted);
    }
}