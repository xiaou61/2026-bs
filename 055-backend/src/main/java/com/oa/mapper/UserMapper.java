package com.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oa.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
