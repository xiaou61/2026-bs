package com.xiaou.herbal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.herbal.entity.Recipe;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecipeMapper extends BaseMapper<Recipe> {
}
