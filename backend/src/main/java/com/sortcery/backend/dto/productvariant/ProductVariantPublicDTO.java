package com.sortcery.backend.dto.productvariant;

public record ProductVariantPublicDTO(
    Long productId,
    String productName,

    Long productVariantId,
    String productVariantName,

    String imageUrl,
    Long sales
) {}

