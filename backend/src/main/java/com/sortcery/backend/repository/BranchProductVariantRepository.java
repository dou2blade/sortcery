package com.sortcery.backend.repository;

import com.sortcery.backend.dto.branchproductvariant.BranchProductVariantSalesIntermediateDTO;
import com.sortcery.backend.model.BranchProductVariant;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchProductVariantRepository extends
    JpaRepository<BranchProductVariant, Long>,
    JpaSpecificationExecutor<BranchProductVariant>
{

    @Query("""
        SELECT new com.sortcery.backend.dto.branchproductvariant.BranchProductVariantSalesIntermediateDTO(
            bpv,
            COALESCE(SUM(ABS(im.quantityChange)), 0)
        )
        FROM BranchProductVariant bpv
        LEFT JOIN InventoryMovement im
            ON im.branchProductVariant = bpv
            AND im.type = 'SALE'
        WHERE (
            :search IS NULL
            OR LOWER(CONCAT(bpv.productVariant.product.name, ' ', bpv.productVariant.name))
                LIKE LOWER(CONCAT('%', :search, '%'))
        )
        AND (
            :category IS NULL
            OR bpv.productVariant.product.productCategory.id = :category
        )
        AND (
            :brand IS NULL
            OR bpv.productVariant.product.brand.id = :brand
        )
        AND (
            :brand IS NULL
            OR bpv.branch.id = :branch
        )
        GROUP BY bpv
    """)
    Page<BranchProductVariantSalesIntermediateDTO> findAllWithSales(
        @Param("search") String search,
        @Param("category") Long category,
        @Param("brand") Long brand,
        @Param("branch") Long branch,
        Pageable pageable
    );

    @Query("""
        SELECT new com.sortcery.backend.dto.branchproductvariant.BranchProductVariantSalesIntermediateDTO(
            bpv,
            COALESCE(SUM(ABS(im.quantityChange)), 0)
        )
        FROM BranchProductVariant bpv
        LEFT JOIN InventoryMovement im
            ON im.branchProductVariant = bpv
            AND im.type = 'SALE'
        WHERE (
            :search IS NULL
            OR LOWER(CONCAT(bpv.productVariant.product.name, ' ', bpv.productVariant.name))
                LIKE LOWER(CONCAT('%', :search, '%'))
        )
        AND (
            :category IS NULL
            OR bpv.productVariant.product.productCategory.id = :category
        )
        AND (
            :brand IS NULL
            OR bpv.productVariant.product.brand.id = :brand
        )
        AND (
            :brand IS NULL
            OR bpv.branch.id = :branch
        )
        GROUP BY bpv
        ORDER BY COALESCE(SUM(ABS(im.quantityChange)), 0) DESC
    """)
    Page<BranchProductVariantSalesIntermediateDTO> findTopWithSales(
        @Param("search") String search,
        @Param("category") Long category,
        @Param("brand") Long brand,
        @Param("branch") Long branch,
        Pageable pageable
    );

    @Query("""
        SELECT new com.sortcery.backend.dto.branchproductvariant.BranchProductVariantSalesIntermediateDTO(
            bpv,
            COALESCE(SUM(ABS(im.quantityChange)), 0)
        )
        FROM BranchProductVariant bpv
        LEFT JOIN InventoryMovement im
            ON im.branchProductVariant = bpv
            AND im.type = 'SALE'
        WHERE bpv.productVariant.id = :variant
        GROUP BY bpv
        ORDER BY bpv.price
    """)
    List<BranchProductVariantSalesIntermediateDTO> findVariantsWithSales(
        @Param("variant") Long variant
    );
}
