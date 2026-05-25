package com.sortcery.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sortcery.backend.dto.product.ProductRequestDTO;
import com.sortcery.backend.dto.product.ProductResponseDTO;
import com.sortcery.backend.exception.NotFoundException;
import com.sortcery.backend.model.Brand;
import com.sortcery.backend.model.Product;
import com.sortcery.backend.model.ProductCategory;
import com.sortcery.backend.repository.BrandRepository;
import com.sortcery.backend.repository.ProductCategoryRepository;
import com.sortcery.backend.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductCategoryRepository productCategoryRepository;

    private final BrandRepository brandRepository;

    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository, BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.brandRepository = brandRepository;
    }

    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll()
            .stream()
            .map((product) -> new ProductResponseDTO(product))
            .toList();
    }

    public ProductResponseDTO findById(Long id) {
        return productRepository.findById(id)
            .map((product) -> new ProductResponseDTO(product))
            .orElseThrow(() -> new NotFoundException(Product.class, id));
    }

    public ProductResponseDTO save(ProductRequestDTO request) {

    ProductCategory productCategory = productCategoryRepository.findById(request.getProductCategoryId())
        .orElseThrow(() -> new NotFoundException(
            ProductCategory.class,
            request.getProductCategoryId()
        ));

    Brand brand = brandRepository.findById(request.getBrandId())
        .orElseThrow(() -> new NotFoundException(
            Brand.class,
            request.getBrandId()
        ));

    Product saved = productRepository.save(new Product(
        productCategory,
        brand,
        request.getName()
    ));

    return new ProductResponseDTO(saved);
}

    public ProductResponseDTO update(Long id, ProductRequestDTO request) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Product.class, id));

        if (request.getName() != null && !request.getName().isBlank()) existing.setName(request.getName());

        Product saved = productRepository.save(existing);

        return new ProductResponseDTO(saved);
    }

    public ProductResponseDTO delete(Long id) {
        Product deleted = productRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Product.class, id));

        productRepository.deleteById(id);

        return new ProductResponseDTO(deleted);
    }
}
