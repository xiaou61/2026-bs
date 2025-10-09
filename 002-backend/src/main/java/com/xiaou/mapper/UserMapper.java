package com.xiaou.mapper;

import com.xiaou.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User findById(Long id);
    
    User findByUsername(String username);
    
    List<User> findAll(@Param("role") String role, @Param("keyword") String keyword);
    
    int insert(User user);
    
    int update(User user);
    
    int deleteById(Long id);
}

