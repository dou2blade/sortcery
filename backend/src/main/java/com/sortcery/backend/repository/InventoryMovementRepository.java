package com.sortcery.backend.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sortcery.backend.model.InventoryMovement;

@Repository
public interface InventoryMovementRepository extends 
    JpaRepository<InventoryMovement, Long>,
    JpaSpecificationExecutor<InventoryMovement>
{
    @Query("""
        SELECT COALESCE(SUM(ABS(im.quantityChange)), 0)
        FROM InventoryMovement im
        WHERE im.type = com.sortcery.backend.model.InventoryMovement.Type.SALE
          AND im.branchProductVariant.branch.id = :branchId
          AND im.createdAt >= :since
    """)
    Long sumSalesSince(Long branchId, LocalDateTime since);
}
