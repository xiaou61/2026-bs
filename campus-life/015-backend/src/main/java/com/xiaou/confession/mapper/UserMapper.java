package com.xiaou.confession.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.confession.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

