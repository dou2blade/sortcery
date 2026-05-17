package com.sortcery.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sortcery.backend.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findBySha256(String sha256);
}
