package com.datamasking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datamasking.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
