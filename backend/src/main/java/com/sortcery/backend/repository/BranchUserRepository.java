package com.sortcery.backend.repository;

import com.sortcery.backend.model.BranchUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchUserRepository extends JpaRepository<BranchUser, Long> {

}