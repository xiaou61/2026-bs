package com.railway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.railway.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Update("UPDATE user SET balance = balance + #{delta} WHERE id = #{userId} AND balance + #{delta} >= 0")
    int changeBalance(@Param("userId") Long userId, @Param("delta") BigDecimal delta);
}
