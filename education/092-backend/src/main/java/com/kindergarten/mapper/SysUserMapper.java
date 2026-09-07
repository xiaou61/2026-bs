package com.kindergarten.mapper;

import com.kindergarten.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    SysUser selectByUsername(@Param("username") String username);
    SysUser selectById(@Param("id") Long id);
    List<SysUser> selectEnabledByRole(@Param("role") String role);
    long countAll();
    long countByRole(@Param("role") String role);
}
