package com.classic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.classic.entity.Ingredient;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IngredientMapper extends BaseMapper<Ingredient> {
}
