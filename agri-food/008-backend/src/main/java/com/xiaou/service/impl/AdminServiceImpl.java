package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.entity.Recipe;
import com.xiaou.entity.User;
import com.xiaou.service.*;
import com.xiaou.vo.StatsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserService userService;
    
    @Autowired
    private RecipeService recipeService;
    
    @Autowired
    private CookingRecordService cookingRecordService;
    
    @Autowired
    private IngredientService ingredientService;

    @Override
    public StatsVO getStats() {
        StatsVO vo = new StatsVO();
        
        Long userCount = userService.count(new LambdaQueryWrapper<User>()
                .eq(User::getRole, "USER"));
        vo.setUserCount(userCount);
        
        Long recipeCount = recipeService.count(new LambdaQueryWrapper<Recipe>()
                .eq(Recipe::getStatus, 1));
        vo.setRecipeCount(recipeCount);
        
        Long cookingRecordCount = cookingRecordService.count();
        vo.setCookingRecordCount(cookingRecordCount);
        
        Long ingredientCount = ingredientService.count();
        vo.setIngredientCount(ingredientCount);
        
        return vo;
    }
}

