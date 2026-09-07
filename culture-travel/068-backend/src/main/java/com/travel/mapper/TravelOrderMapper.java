package com.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.entity.TravelOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TravelOrderMapper extends BaseMapper<TravelOrder> {
}
