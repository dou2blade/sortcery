package com.sortcery.backend.dto.productvariant;

public record ProductVariantSalesDTO(
    Long productId,
    String productName,

    Long productVariantId,
    String productVariantName,

    String imageUrl,
    Long totalSales
) {}

