package com.sortcery.backend.service;

import com.sortcery.backend.model.Brand;
import com.sortcery.backend.dto.brand.BrandOptionDTO;
import com.sortcery.backend.dto.brand.BrandRequestDTO;
import com.sortcery.backend.dto.brand.BrandResponseDTO;
import com.sortcery.backend.repository.BrandRepository;
import com.sortcery.backend.exception.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Page<BrandResponseDTO> findPage(
        int page,
        int size,
        String search,
        Sort sort
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Specification<Brand> spec = (root, query, cb) -> cb.conjunction();

        if (search != null && !search.isBlank()) {
            String term = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> (
                cb.like(cb.lower(root.get("name")), term)
            ));
        }

        return brandRepository
            .findAll(spec, pageRequest)
            .map(BrandResponseDTO::new);
    }

    public List<BrandOptionDTO> findOptions() {
        return brandRepository.findAll()
            .stream()
            .map(BrandOptionDTO::new)
            .toList();
    }

    public BrandResponseDTO findById(Long id) {
        return brandRepository.findById(id)
            .map((brand) -> new BrandResponseDTO(brand))
            .orElseThrow(() -> new NotFoundException(Brand.class, id));
    }

    public BrandResponseDTO save(BrandRequestDTO request) {
        Brand saved = brandRepository.save(new Brand(
            request.getName()
        ));
        return new BrandResponseDTO(saved);
    }

    public BrandResponseDTO update(Long id, BrandRequestDTO request) {
        Brand existing = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Brand.class, id));

        if (request.getName() != null && !request.getName().isBlank()) existing.setName(request.getName());

        Brand saved = brandRepository.save(existing);

        return new BrandResponseDTO(saved);
    }

    public BrandResponseDTO delete(Long id) {
        Brand deleted = brandRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Brand.class, id));

        brandRepository.deleteById(id);

        return new BrandResponseDTO(deleted);
    }
}
