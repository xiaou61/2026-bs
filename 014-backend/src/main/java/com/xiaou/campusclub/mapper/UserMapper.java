package com.xiaou.campusclub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.campusclub.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

