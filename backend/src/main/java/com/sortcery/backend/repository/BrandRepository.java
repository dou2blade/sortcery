package com.sortcery.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.sortcery.backend.model.Brand;

@Repository
public interface BrandRepository extends 
    JpaRepository<Brand, Long>, 
    JpaSpecificationExecutor<Brand> 
{

}
