package com.xiaou.sport.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.sport.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
