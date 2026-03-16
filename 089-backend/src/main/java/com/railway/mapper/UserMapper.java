package com.railway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.railway.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
