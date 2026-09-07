package com.xiaou.herbal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.herbal.dto.RecipeCreateRequest;
import com.xiaou.herbal.entity.Recipe;

import java.util.List;

public interface RecipeService extends IService<Recipe> {

    void createRecipe(Long userId, RecipeCreateRequest request);

    Recipe getRecipeDetail(Long recipeId);

    List<Recipe> listPublishedRecipes(Integer page, Integer pageSize);

    List<Recipe> searchRecipes(String keyword);

    void updateRecipe(Long recipeId, RecipeCreateRequest request);

    void deleteRecipe(Long recipeId);

    List<Recipe> getUserRecipes(Long userId);

    void increaseViews(Long recipeId);

    void collectRecipe(Long userId, Long recipeId);

    boolean isCollected(Long userId, Long recipeId);
}
