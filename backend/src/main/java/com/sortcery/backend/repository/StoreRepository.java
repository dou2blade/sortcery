package com.sortcery.backend.repository;

import com.sortcery.backend.model.Store;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends 
    JpaRepository<Store, Long>,
    JpaSpecificationExecutor<Store> 
{
    Optional<Store> findByName(String name);
}
