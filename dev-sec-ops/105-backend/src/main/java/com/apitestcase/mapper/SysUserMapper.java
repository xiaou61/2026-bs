package com.apitestcase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
