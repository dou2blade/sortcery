package com.sortcery.backend.service;

import org.springframework.stereotype.Service;

import com.sortcery.backend.dto.PublicStatsDTO;
import com.sortcery.backend.repository.BrandRepository;
import com.sortcery.backend.repository.ProductCategoryRepository;
import com.sortcery.backend.repository.ProductRepository;
import com.sortcery.backend.repository.StoreRepository;


@Service
public class PublicStatsService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final BrandRepository brandRepository;
    private final StoreRepository storeRepository;

    public PublicStatsService(
        ProductRepository productRepository,
		ProductCategoryRepository productCategoryRepository,
		BrandRepository brandRepository,
        StoreRepository storeRepository
    ) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.brandRepository = brandRepository;
        this.storeRepository = storeRepository;
    }

    public PublicStatsDTO stats() {
        return new PublicStatsDTO(
            productRepository.count(), 
            brandRepository.count(),
            productCategoryRepository.count(),
            storeRepository.count()
        );
    }
}
