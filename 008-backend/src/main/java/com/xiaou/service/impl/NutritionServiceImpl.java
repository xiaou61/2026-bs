package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.entity.*;
import com.xiaou.service.*;
import com.xiaou.vo.NutritionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class NutritionServiceImpl implements NutritionService {

    @Autowired
    private RecipeIngredientService recipeIngredientService;
    
    @Autowired
    private IngredientService ingredientService;
    
    @Autowired
    private CookingRecordService cookingRecordService;
    
    @Autowired
    private RecipeService recipeService;

    @Override
    public NutritionVO getRecipeNutrition(Long recipeId) {
        List<RecipeIngredient> ingredients = recipeIngredientService.list(
                new LambdaQueryWrapper<RecipeIngredient>()
                        .eq(RecipeIngredient::getRecipeId, recipeId));
        
        NutritionVO vo = new NutritionVO();
        BigDecimal totalCalories = BigDecimal.ZERO;
        BigDecimal totalProtein = BigDecimal.ZERO;
        BigDecimal totalFat = BigDecimal.ZERO;
        BigDecimal totalCarbohydrate = BigDecimal.ZERO;
        BigDecimal totalFiber = BigDecimal.ZERO;
        
        for (RecipeIngredient ri : ingredients) {
            Ingredient ingredient = ingredientService.getById(ri.getIngredientId());
            if (ingredient != null) {
                BigDecimal ratio = ri.getQuantity().divide(new BigDecimal("100"), 4, BigDecimal.ROUND_HALF_UP);
                
                totalCalories = totalCalories.add(ingredient.getCalories().multiply(ratio));
                totalProtein = totalProtein.add(ingredient.getProtein().multiply(ratio));
                totalFat = totalFat.add(ingredient.getFat().multiply(ratio));
                totalCarbohydrate = totalCarbohydrate.add(ingredient.getCarbohydrate().multiply(ratio));
                totalFiber = totalFiber.add(ingredient.getFiber().multiply(ratio));
            }
        }
        
        vo.setTotalCalories(totalCalories.setScale(2, BigDecimal.ROUND_HALF_UP));
        vo.setTotalProtein(totalProtein.setScale(2, BigDecimal.ROUND_HALF_UP));
        vo.setTotalFat(totalFat.setScale(2, BigDecimal.ROUND_HALF_UP));
        vo.setTotalCarbohydrate(totalCarbohydrate.setScale(2, BigDecimal.ROUND_HALF_UP));
        vo.setTotalFiber(totalFiber.setScale(2, BigDecimal.ROUND_HALF_UP));
        
        return vo;
    }

    @Override
    public NutritionVO getDailyNutrition(Long userId) {
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        
        List<CookingRecord> todayRecords = cookingRecordService.list(
                new LambdaQueryWrapper<CookingRecord>()
                        .eq(CookingRecord::getUserId, userId)
                        .between(CookingRecord::getCreateTime, startOfDay, endOfDay));
        
        NutritionVO vo = new NutritionVO();
        BigDecimal totalCalories = BigDecimal.ZERO;
        BigDecimal totalProtein = BigDecimal.ZERO;
        BigDecimal totalFat = BigDecimal.ZERO;
        BigDecimal totalCarbohydrate = BigDecimal.ZERO;
        BigDecimal totalFiber = BigDecimal.ZERO;
        
        for (CookingRecord record : todayRecords) {
            NutritionVO recipeNutrition = getRecipeNutrition(record.getRecipeId());
            totalCalories = totalCalories.add(recipeNutrition.getTotalCalories());
            totalProtein = totalProtein.add(recipeNutrition.getTotalProtein());
            totalFat = totalFat.add(recipeNutrition.getTotalFat());
            totalCarbohydrate = totalCarbohydrate.add(recipeNutrition.getTotalCarbohydrate());
            totalFiber = totalFiber.add(recipeNutrition.getTotalFiber());
        }
        
        vo.setTotalCalories(totalCalories);
        vo.setTotalProtein(totalProtein);
        vo.setTotalFat(totalFat);
        vo.setTotalCarbohydrate(totalCarbohydrate);
        vo.setTotalFiber(totalFiber);
        
        return vo;
    }
}

