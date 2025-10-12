package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Ingredient;
import com.xiaou.mapper.IngredientMapper;
import com.xiaou.service.IngredientService;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl extends ServiceImpl<IngredientMapper, Ingredient> implements IngredientService {

    @Override
    public IPage<Ingredient> getIngredientList(Integer pageNum, Integer pageSize, String category, String name) {
        Page<Ingredient> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Ingredient> wrapper = new LambdaQueryWrapper<>();
        
        if (category != null && !category.isEmpty()) {
            wrapper.eq(Ingredient::getCategory, category);
        }
        
        if (name != null && !name.isEmpty()) {
            wrapper.like(Ingredient::getName, name);
        }
        
        return page(page, wrapper);
    }
}

