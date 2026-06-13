package com.sortcery.backend.repository;

import com.sortcery.backend.dto.product.ProductSalesDTO;
import com.sortcery.backend.model.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends 
    JpaRepository<Product, Long>,
    JpaSpecificationExecutor<Product>
{
    Optional<Product> findByName(String name);

    @Query("""
        SELECT new com.sortcery.backend.dto.product.ProductSalesDTO(
            p.id, 
            p.name,
            p.imageUrl,
            SUM(ABS(im.quantityChange))
        )
        FROM Product p
        JOIN p.productVariants pv
        JOIN pv.branchProductVariants bpv
        JOIN InventoryMovement im ON im.branchProductVariant = bpv
        WHERE im.type = 'SALE'
        GROUP BY p.id, p.name, p.imageUrl
        ORDER BY SUM(ABS(im.quantityChange)) DESC
    """)
    List<ProductSalesDTO> findTop();

    @Query("""
        SELECT new com.sortcery.backend.dto.product.ProductSalesDTO(
            p.id,
            p.name,
            p.imageUrl,
            SUM(ABS(im.quantityChange))
        )
        FROM Product p
        JOIN p.productVariants pv
        JOIN pv.branchProductVariants bpv
        JOIN InventoryMovement im ON im.branchProductVariant = bpv
        WHERE im.type = 'SALE'
          AND bpv.branch.id IN :branchIds
        GROUP BY p.id, p.name, p.imageUrl
        ORDER BY SUM(ABS(im.quantityChange)) DESC
    """)
    List<ProductSalesDTO> findTopByBranches(List<Long> branchIds);
}
