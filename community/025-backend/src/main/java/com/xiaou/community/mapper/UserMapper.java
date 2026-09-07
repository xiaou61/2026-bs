package com.xiaou.community.mapper;

import com.xiaou.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {
    User findByUsername(String username);
    void insert(User user);
    User findById(Integer id);
    void update(User user);
    void deleteById(Integer id);
    List<User> findAll();
}
