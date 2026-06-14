package com.sortcery.backend.dto.branchproductvariant;

import com.sortcery.backend.model.BranchProductVariant;

public record BranchProductVariantSalesIntermediateDTO (
    BranchProductVariant bpv,
    Long sales
) {}
