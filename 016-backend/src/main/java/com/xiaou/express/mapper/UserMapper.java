package com.xiaou.express.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.express.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

