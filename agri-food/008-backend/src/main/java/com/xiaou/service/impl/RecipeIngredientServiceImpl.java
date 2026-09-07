package com.xiaou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.RecipeIngredient;
import com.xiaou.mapper.RecipeIngredientMapper;
import com.xiaou.service.RecipeIngredientService;
import org.springframework.stereotype.Service;

@Service
public class RecipeIngredientServiceImpl extends ServiceImpl<RecipeIngredientMapper, RecipeIngredient> implements RecipeIngredientService {
}

