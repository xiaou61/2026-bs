package com.community.parking.repository;

import com.community.parking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    Optional<User> findByUsername(String username);
    
    Optional<User> findByPhone(String phone);
    
    boolean existsByUsername(String username);
    
    boolean existsByPhone(String phone);
}
