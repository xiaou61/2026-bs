package com.classic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.classic.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
