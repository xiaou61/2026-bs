package com.agritrace.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.agritrace.entity.FarmerProfile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FarmerProfileMapper extends BaseMapper<FarmerProfile> {
}
