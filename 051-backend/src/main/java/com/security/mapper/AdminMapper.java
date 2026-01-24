package com.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
}
