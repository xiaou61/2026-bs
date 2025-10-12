package com.xiaou.service;

import com.xiaou.entity.Recipe;

import java.util.List;

public interface RecommendService {
    
    List<Recipe> getTodayRecommend(Long userId);
    
    List<Recipe> getByIngredient(Long userId);
    
    List<Recipe> getHotRecipes();
    
    List<Recipe> getEasyRecipes();
}

