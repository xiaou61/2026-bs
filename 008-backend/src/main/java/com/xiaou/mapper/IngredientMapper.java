package com.xiaou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.entity.Ingredient;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IngredientMapper extends BaseMapper<Ingredient> {
}

