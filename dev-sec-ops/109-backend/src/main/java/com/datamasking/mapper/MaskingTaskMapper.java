package com.datamasking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datamasking.entity.MaskingTask;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MaskingTaskMapper extends BaseMapper<MaskingTask> {
}
