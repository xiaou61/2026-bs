package com.teachres.mapper;

import com.teachres.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    SysUser selectById(@Param("id") Long id);

    SysUser selectByUsername(@Param("username") String username);

    List<SysUser> selectList(@Param("username") String username,
                             @Param("role") String role,
                             @Param("status") Integer status);

    int countByUsername(@Param("username") String username);

    int insert(SysUser user);

    int update(SysUser user);

    int deleteById(@Param("id") Long id);
}
