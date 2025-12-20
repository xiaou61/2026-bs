package com.xiaou.snack.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.snack.wms.entity.basic.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
