package com.hospital.mapper;

import com.hospital.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    SysUser selectByUsername(@Param("username") String username);

    SysUser selectById(@Param("id") Long id);

    List<SysUser> selectPage(@Param("role") String role, @Param("keyword") String keyword, @Param("status") Integer status);

    void insert(SysUser entity);

    void update(SysUser entity);

    void updateStatus(@Param("id") Long id, @Param("status") Integer status);

    void deleteById(@Param("id") Long id);

    long countAll();

    long countByRole(@Param("role") String role);
}
