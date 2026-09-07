package com.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.entity.Traveler;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TravelerMapper extends BaseMapper<Traveler> {
}
