package com.sortcery.backend.dto.product;

public record ProductSalesDTO(
    Long id,
    String name,

    String imageUrl,
    Long totalSales
) {}

