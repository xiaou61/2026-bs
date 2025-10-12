package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Ingredient;

public interface IngredientService extends IService<Ingredient> {
    
    IPage<Ingredient> getIngredientList(Integer pageNum, Integer pageSize, String category, String name);
}

