package com.diet.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diet.management.entity.Food;
import org.apache.ibatis.annotations.Mapper;

/**
 * 食物Mapper
 */
@Mapper
public interface FoodMapper extends BaseMapper<Food> {
}
