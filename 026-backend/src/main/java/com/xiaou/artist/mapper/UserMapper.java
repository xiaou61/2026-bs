package com.xiaou.artist.mapper;

import com.xiaou.artist.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User selectById(Long id);
    User selectByUsername(String username);
    List<User> selectAll();
    int insert(User user);
    int update(User user);
    int deleteById(Long id);
    int updatePassword(@Param("id") Long id, @Param("password") String password);
    int updateBalance(@Param("id") Long id, @Param("amount") java.math.BigDecimal amount);
}
