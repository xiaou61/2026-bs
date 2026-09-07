package com.xiaou.pet.mapper;

import com.xiaou.pet.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserMapper {
    User selectByUsername(String username);
    int insert(User user);
    User selectById(Long id);
    int update(User user);
    List<User> selectAll();
}
