package com.movie.mapper;

import com.movie.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserMapper {
    User selectById(Long id);
    User selectByUsername(String username);
    List<User> selectPage(@Param("username") String username);
    int insert(User user);
    int update(User user);
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    int deleteById(Long id);
    int count();
}
