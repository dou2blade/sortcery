package com.sortcery.backend.dto.product;

public record ProductStatsDTO(
    long totalProducts,
    long totalBrands,
    long totalProductCategories
) {}
