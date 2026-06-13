package com.sortcery.backend.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sortcery.backend.dto.product.ProductOptionDTO;
import com.sortcery.backend.dto.product.ProductRequestDTO;
import com.sortcery.backend.dto.product.ProductResponseDTO;
import com.sortcery.backend.dto.product.ProductSalesDTO;
import com.sortcery.backend.dto.product.ProductStatsDTO;
import com.sortcery.backend.exception.NotFoundException;
import com.sortcery.backend.model.Brand;
import com.sortcery.backend.model.Product;
import com.sortcery.backend.model.ProductCategory;
import com.sortcery.backend.repository.BrandRepository;
import com.sortcery.backend.repository.ProductCategoryRepository;
import com.sortcery.backend.repository.ProductRepository;
import com.sortcery.backend.repository.ProductVariantRepository;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final BrandRepository brandRepository;
    private final ProductVariantRepository productVariantRepository;

    public ProductService(
        ProductRepository productRepository,
		ProductCategoryRepository productCategoryRepository,
		BrandRepository brandRepository,
        ProductVariantRepository productVariantRepository
    ) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.brandRepository = brandRepository;
        this.productVariantRepository = productVariantRepository;
    }

    public Page<ProductResponseDTO> findPage(
        int page,
        int size,
        String search,
        Sort sort
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Specification<Product> spec = (root, query, cb) -> cb.conjunction();

        if (search != null && !search.isBlank()) {
            String term = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> (
                cb.like(cb.lower(root.get("name")), term)
            ));
        }

        return productRepository
            .findAll(spec, pageRequest)
            .map(ProductResponseDTO::new);
    }

    public List<ProductOptionDTO> findOptions() {
        return productRepository.findAll()
            .stream()
            .map(ProductOptionDTO::new)
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
        request.getName(),
        request.getImageUrl()
    ));

    return new ProductResponseDTO(saved);
}

    public ProductResponseDTO update(Long id, ProductRequestDTO request) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Product.class, id));

        if (request.getName() != null && !request.getName().isBlank()) existing.setName(request.getName());
        if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) existing.setImageUrl(request.getImageUrl());

        Product saved = productRepository.save(existing);

        return new ProductResponseDTO(saved);
    }

    public ProductResponseDTO delete(Long id) {
        Product deleted = productRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Product.class, id));

        productRepository.deleteById(id);

        return new ProductResponseDTO(deleted);
    }

    public ProductStatsDTO stats() {
        return new ProductStatsDTO(
            productRepository.count(), 
            brandRepository.count(),
            productCategoryRepository.count(),
            productVariantRepository.count()
        );
    }

    public List<ProductSalesDTO> findTop(int size, Double longitude, Double latitude) { 
        return productRepository.findTop().subList(0, size);
    }
}
