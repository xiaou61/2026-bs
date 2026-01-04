package com.diet.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diet.management.entity.DietRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 饮食记录Mapper
 */
@Mapper
public interface DietRecordMapper extends BaseMapper<DietRecord> {
}
