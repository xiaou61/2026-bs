package com.xiaou.service;

import com.xiaou.vo.NutritionVO;

public interface NutritionService {
    
    NutritionVO getRecipeNutrition(Long recipeId);
    
    NutritionVO getDailyNutrition(Long userId);
}

