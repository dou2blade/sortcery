package com.sortcery.backend.repository;

import com.sortcery.backend.model.Product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends 
    JpaRepository<Product, Long>,
    JpaSpecificationExecutor<Product>
{
    Optional<Product> findByName(String name);
}
