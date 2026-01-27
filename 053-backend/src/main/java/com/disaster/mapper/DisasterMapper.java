package com.disaster.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.disaster.entity.Disaster;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DisasterMapper extends BaseMapper<Disaster> {
}
