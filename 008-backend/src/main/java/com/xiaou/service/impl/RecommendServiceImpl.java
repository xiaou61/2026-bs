package com.xiaou.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.entity.*;
import com.xiaou.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private RecipeService recipeService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserIngredientService userIngredientService;
    
    @Autowired
    private RecipeIngredientService recipeIngredientService;
    
    @Autowired
    private CookingRecordService cookingRecordService;

    @Override
    public List<Recipe> getTodayRecommend(Long userId) {
        User user = userService.getById(userId);
        List<Recipe> allRecipes = recipeService.list(new LambdaQueryWrapper<Recipe>()
                .eq(Recipe::getStatus, 1));
        
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);
        List<CookingRecord> recentRecords = cookingRecordService.list(
                new LambdaQueryWrapper<CookingRecord>()
                        .eq(CookingRecord::getUserId, userId)
                        .ge(CookingRecord::getCreateTime, threeDaysAgo));
        
        Set<Long> recentRecipeIds = recentRecords.stream()
                .map(CookingRecord::getRecipeId)
                .collect(Collectors.toSet());
        
        List<Recipe> candidateRecipes = allRecipes.stream()
                .filter(r -> !recentRecipeIds.contains(r.getId()))
                .filter(r -> !hasDietTaboo(r, user))
                .collect(Collectors.toList());
        
        Map<Long, Double> scoreMap = new HashMap<>();
        for (Recipe recipe : candidateRecipes) {
            double score = calculateRecommendScore(recipe, user, userId);
            scoreMap.put(recipe.getId(), score);
        }
        
        return candidateRecipes.stream()
                .sorted((r1, r2) -> scoreMap.get(r2.getId()).compareTo(scoreMap.get(r1.getId())))
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public List<Recipe> getByIngredient(Long userId) {
        List<UserIngredient> userIngredients = userIngredientService.list(
                new LambdaQueryWrapper<UserIngredient>()
                        .eq(UserIngredient::getUserId, userId)
                        .eq(UserIngredient::getStatus, 1));
        
        if (userIngredients.isEmpty()) {
            return new ArrayList<>();
        }
        
        Set<Long> ownedIngredientIds = userIngredients.stream()
                .map(UserIngredient::getIngredientId)
                .collect(Collectors.toSet());
        
        List<Recipe> allRecipes = recipeService.list(new LambdaQueryWrapper<Recipe>()
                .eq(Recipe::getStatus, 1));
        
        Map<Long, Double> matchMap = new HashMap<>();
        for (Recipe recipe : allRecipes) {
            List<RecipeIngredient> requiredIngredients = recipeIngredientService.list(
                    new LambdaQueryWrapper<RecipeIngredient>()
                            .eq(RecipeIngredient::getRecipeId, recipe.getId())
                            .eq(RecipeIngredient::getIsRequired, 1));
            
            if (requiredIngredients.isEmpty()) {
                continue;
            }
            
            long matchCount = requiredIngredients.stream()
                    .filter(ri -> ownedIngredientIds.contains(ri.getIngredientId()))
                    .count();
            
            double matchRate = (double) matchCount / requiredIngredients.size();
            if (matchRate > 0.3) {
                matchMap.put(recipe.getId(), matchRate);
            }
        }
        
        return allRecipes.stream()
                .filter(r -> matchMap.containsKey(r.getId()))
                .sorted((r1, r2) -> matchMap.get(r2.getId()).compareTo(matchMap.get(r1.getId())))
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public List<Recipe> getHotRecipes() {
        return recipeService.list(new LambdaQueryWrapper<Recipe>()
                .eq(Recipe::getStatus, 1)
                .orderByDesc(Recipe::getViewCount)
                .orderByDesc(Recipe::getRatingScore)
                .last("LIMIT 10"));
    }

    @Override
    public List<Recipe> getEasyRecipes() {
        return recipeService.list(new LambdaQueryWrapper<Recipe>()
                .eq(Recipe::getStatus, 1)
                .eq(Recipe::getDifficulty, 1)
                .orderByDesc(Recipe::getRatingScore)
                .last("LIMIT 10"));
    }

    private boolean hasDietTaboo(Recipe recipe, User user) {
        if (user.getDietTaboo() == null || user.getDietTaboo().isEmpty()) {
            return false;
        }
        
        try {
            List<String> taboos = JSON.parseArray(user.getDietTaboo(), String.class);
            List<RecipeIngredient> ingredients = recipeIngredientService.list(
                    new LambdaQueryWrapper<RecipeIngredient>()
                            .eq(RecipeIngredient::getRecipeId, recipe.getId()));
            
            for (RecipeIngredient ri : ingredients) {
                for (String taboo : taboos) {
                    if (taboo != null && !taboo.isEmpty()) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        
        return false;
    }

    private double calculateRecommendScore(Recipe recipe, User user, Long userId) {
        double ingredientScore = 0.0;
        List<UserIngredient> userIngredients = userIngredientService.list(
                new LambdaQueryWrapper<UserIngredient>()
                        .eq(UserIngredient::getUserId, userId)
                        .eq(UserIngredient::getStatus, 1));
        
        if (!userIngredients.isEmpty()) {
            Set<Long> ownedIds = userIngredients.stream()
                    .map(UserIngredient::getIngredientId)
                    .collect(Collectors.toSet());
            
            List<RecipeIngredient> requiredIngredients = recipeIngredientService.list(
                    new LambdaQueryWrapper<RecipeIngredient>()
                            .eq(RecipeIngredient::getRecipeId, recipe.getId())
                            .eq(RecipeIngredient::getIsRequired, 1));
            
            if (!requiredIngredients.isEmpty()) {
                long matchCount = requiredIngredients.stream()
                        .filter(ri -> ownedIds.contains(ri.getIngredientId()))
                        .count();
                ingredientScore = (double) matchCount / requiredIngredients.size();
            }
        }
        
        double tasteScore = 0.2;
        
        double historyScore = 0.2;
        
        double ratingScore = recipe.getRatingScore().doubleValue() / 5.0;
        
        double timeScore = 0.1;
        
        return ingredientScore * 0.4 + tasteScore * 0.2 + historyScore * 0.2 + ratingScore * 0.1 + timeScore * 0.1;
    }
}

