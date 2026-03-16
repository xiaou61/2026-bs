package com.railway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.railway.entity.Train;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TrainMapper extends BaseMapper<Train> {
}
