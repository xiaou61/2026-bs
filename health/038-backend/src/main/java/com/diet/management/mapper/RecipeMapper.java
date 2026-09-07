package com.diet.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diet.management.entity.Recipe;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecipeMapper extends BaseMapper<Recipe> {
}
