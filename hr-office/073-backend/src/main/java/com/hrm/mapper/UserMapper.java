package com.hrm.mapper;

import com.hrm.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserMapper {
    User selectById(Long id);
    User selectByUsername(String username);
    List<User> selectList(@Param("username") String username, @Param("role") String role);
    int insert(User user);
    int update(User user);
    int deleteById(Long id);
    int countByUsername(String username);
}
