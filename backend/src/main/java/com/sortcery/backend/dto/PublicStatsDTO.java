package com.sortcery.backend.dto;

public record PublicStatsDTO(
    long totalProducts,
    long totalBrands,
    long totalProductCategories,
    long totalStores
) {}
