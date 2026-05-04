package com.recruitmatch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.recruitmatch.entity.JobRequirement;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JobRequirementMapper extends BaseMapper<JobRequirement> {
}
