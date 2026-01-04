package com.diet.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diet.management.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
