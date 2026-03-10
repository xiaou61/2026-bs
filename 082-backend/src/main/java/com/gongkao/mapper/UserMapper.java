package com.gongkao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongkao.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

