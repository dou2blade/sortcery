package com.sortcery.backend.repository;

import com.sortcery.backend.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends
    JpaRepository<User, Long>,
    JpaSpecificationExecutor<User> 
{
    Optional<User> findByEmail(String email);
    List<User> findByEmailContaining(String text);

    @Query("""
    SELECT u.role, COUNT(u)
    FROM User u
    GROUP BY u.role
    """)
    List<Object[]> countByRole();
}
