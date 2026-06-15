package com.sortcery.backend.algorithms;

import com.sortcery.backend.dto.branchproductvariant.BranchProductVariantPublicDTO;

public final class ProductScorer {

    private ProductScorer() {}

    public static double score(
        BranchProductVariantPublicDTO product,
        String search
    ) {
        double score = 0;

        // match pos
        if (search != null && !search.isBlank()) {

            String text =
                product.getProductName() + " " +
                product.getProductVariantName();

            int pos = BoyerMoore.indexOf(text, search);

            if (pos >= 0) {
                score += pos;
            } else {
                score += 10_000;
            }
        }

        // distance bonus
        if (product.getDistance() != null) {
            score += product.getDistance() * 5;
        }

        // sales bonus
        score -= product.getSales() * 2;

        return score;
    }
}
