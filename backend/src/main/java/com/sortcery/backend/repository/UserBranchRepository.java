package com.sortcery.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sortcery.backend.model.UserBranch;

@Repository
public interface UserBranchRepository extends JpaRepository<UserBranch, Long> {
    List<UserBranch> findByBranchId(Long branchId);
    List<UserBranch> findByUserId(Long userId);

    Optional<UserBranch> findByUserIdAndBranchId(Long userId, Long branchId);

    boolean existsByUserIdAndBranchId(Long userId, Long branchId);
}
