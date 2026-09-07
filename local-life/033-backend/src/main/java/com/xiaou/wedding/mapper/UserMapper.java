package com.xiaou.wedding.mapper;

import com.xiaou.wedding.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User findByUsername(@Param("username") String username);
    
    User findById(@Param("id") Long id);
    
    int insert(User user);
    
    int updateById(User user);
}
