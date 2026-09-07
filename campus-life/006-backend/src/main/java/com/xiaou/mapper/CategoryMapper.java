package com.xiaou.mapper;

import com.xiaou.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> findAll();
    
    List<Category> findByStatus(@Param("status") Integer status);
    
    Category findById(@Param("id") Long id);
    
    int insert(Category category);
    
    int update(Category category);
    
    int deleteById(@Param("id") Long id);
}

