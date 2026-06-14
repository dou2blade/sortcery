package com.sortcery.backend.repository;

import com.sortcery.backend.dto.productvariant.ProductVariantPublicDTO;
import com.sortcery.backend.model.ProductVariant;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends 
    JpaRepository<ProductVariant, Long>,
    JpaSpecificationExecutor<ProductVariant>
{
    List<ProductVariant> findByProductId(Long productId);

    @Query("""
        SELECT new com.sortcery.backend.dto.productvariant.ProductVariantPublicDTO(
            p.id,
            p.name,
            pv.id,
            pv.name,
            pv.imageUrl,
            SUM(im.quantityChange)
        )
        FROM Product p
        JOIN p.productVariants pv
        JOIN pv.branchProductVariants bpv
        JOIN InventoryMovement im ON im.branchProductVariant = bpv
        WHERE im.type = 'SALE'
        GROUP BY p.id, p.name, pv.id, pv.name, pv.imageUrl
    """)
    Page<ProductVariantPublicDTO> findTopVariants(
        @Param("search") String search,
        Pageable pageable
    );
}
