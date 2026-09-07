package com.folksong.platform.repository;

import com.folksong.platform.entity.Category;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    
    @Query("SELECT * FROM categories WHERE status = 1 ORDER BY sort_order ASC")
    List<Category> findAllActive();
    
    @Query("SELECT * FROM categories ORDER BY sort_order ASC LIMIT :limit OFFSET :offset")
    List<Category> findAllWithPage(@Param("limit") Integer limit, @Param("offset") Integer offset);
    
    boolean existsByName(String name);
    
    @Query("SELECT * FROM categories WHERE region = :region AND status = 1 ORDER BY sort_order ASC")
    List<Category> findByRegion(@Param("region") String region);
}
