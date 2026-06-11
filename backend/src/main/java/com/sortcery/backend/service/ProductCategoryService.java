package com.sortcery.backend.service;

import com.sortcery.backend.model.ProductCategory;
import com.sortcery.backend.dto.productcategory.ProductCategoryOptionDTO;
import com.sortcery.backend.dto.productcategory.ProductCategoryRequestDTO;
import com.sortcery.backend.dto.productcategory.ProductCategoryResponseDTO;
import com.sortcery.backend.repository.ProductCategoryRepository;
import com.sortcery.backend.exception.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public Page<ProductCategoryResponseDTO> findPage(
        int page,
        int size,
        String search,
        Sort sort
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Specification<ProductCategory> spec = (root, query, cb) -> cb.conjunction();

        if (search != null && !search.isBlank()) {
            String term = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> (
                cb.like(cb.lower(root.get("name")), term)
            ));
        }

        return productCategoryRepository
            .findAll(spec, pageRequest)
            .map(ProductCategoryResponseDTO::new);
    }

    public List<ProductCategoryOptionDTO> findOptions() {
        return productCategoryRepository.findAll()
            .stream()
            .map(ProductCategoryOptionDTO::new)
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
