package com.online.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
