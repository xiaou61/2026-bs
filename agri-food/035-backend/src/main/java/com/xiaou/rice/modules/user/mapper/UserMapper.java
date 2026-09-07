package com.xiaou.rice.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.rice.modules.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where username = #{username} limit 1")
    User findByUsername(@Param("username") String username);
}
