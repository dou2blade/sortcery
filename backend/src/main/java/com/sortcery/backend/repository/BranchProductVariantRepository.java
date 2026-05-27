package com.sortcery.backend.repository;

import com.sortcery.backend.model.BranchProductVariant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchProductVariantRepository extends JpaRepository<BranchProductVariant, Long> {
}
