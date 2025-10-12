package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.dto.RecipeCreateDTO;
import com.xiaou.dto.RecipeQueryDTO;
import com.xiaou.entity.Recipe;
import com.xiaou.vo.RecipeDetailVO;

import java.util.List;

public interface RecipeService extends IService<Recipe> {
    
    IPage<Recipe> getRecipeList(RecipeQueryDTO dto);
    
    RecipeDetailVO getRecipeDetail(Long recipeId, Long userId);
    
    List<Recipe> searchRecipes(String keyword);
    
    void createRecipe(RecipeCreateDTO dto);
    
    void updateRecipe(Long recipeId, RecipeCreateDTO dto);
    
    void deleteRecipe(Long recipeId);
    
    void incrementViewCount(Long recipeId);
}

