package com.sortcery.backend.repository;

import com.sortcery.backend.model.ProductVariant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends 
    JpaRepository<ProductVariant, Long>,
    JpaSpecificationExecutor<ProductVariant>
{
    List<ProductVariant> findByProductId(Long productId);
}
