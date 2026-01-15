package com.folksong.platform.repository;

import com.folksong.platform.entity.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    boolean existsByPhone(String phone);
    
    @Query("SELECT * FROM users WHERE status = :status ORDER BY create_time DESC LIMIT :limit OFFSET :offset")
    List<User> findByStatusWithPage(@Param("status") Integer status, @Param("limit") Integer limit, @Param("offset") Integer offset);
    
    @Query("SELECT COUNT(*) FROM users WHERE status = :status")
    Long countByStatus(@Param("status") Integer status);
    
    @Query("SELECT * FROM users ORDER BY create_time DESC LIMIT :limit OFFSET :offset")
    List<User> findAllWithPage(@Param("limit") Integer limit, @Param("offset") Integer offset);
    
    @Modifying
    @Query("UPDATE users SET status = :status WHERE id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
