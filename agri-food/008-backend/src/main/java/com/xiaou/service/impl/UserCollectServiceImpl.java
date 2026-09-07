package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Recipe;
import com.xiaou.entity.UserCollect;
import com.xiaou.exception.BusinessException;
import com.xiaou.mapper.UserCollectMapper;
import com.xiaou.service.RecipeService;
import com.xiaou.service.UserCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCollectServiceImpl extends ServiceImpl<UserCollectMapper, UserCollect> implements UserCollectService {

    @Autowired
    private RecipeService recipeService;

    @Override
    @Transactional
    public void collectRecipe(Long userId, Long recipeId) {
        Long count = count(new LambdaQueryWrapper<UserCollect>()
                .eq(UserCollect::getUserId, userId)
                .eq(UserCollect::getRecipeId, recipeId));
        
        if (count > 0) {
            throw new BusinessException("已经收藏过该菜谱");
        }
        
        UserCollect collect = new UserCollect();
        collect.setUserId(userId);
        collect.setRecipeId(recipeId);
        save(collect);
        
        Recipe recipe = recipeService.getById(recipeId);
        if (recipe != null) {
            recipe.setCollectCount(recipe.getCollectCount() + 1);
            recipeService.updateById(recipe);
        }
    }

    @Override
    @Transactional
    public void cancelCollect(Long userId, Long recipeId) {
        remove(new LambdaQueryWrapper<UserCollect>()
                .eq(UserCollect::getUserId, userId)
                .eq(UserCollect::getRecipeId, recipeId));
        
        Recipe recipe = recipeService.getById(recipeId);
        if (recipe != null && recipe.getCollectCount() > 0) {
            recipe.setCollectCount(recipe.getCollectCount() - 1);
            recipeService.updateById(recipe);
        }
    }

    @Override
    public List<Recipe> getCollectList(Long userId) {
        List<UserCollect> collects = list(new LambdaQueryWrapper<UserCollect>()
                .eq(UserCollect::getUserId, userId)
                .orderByDesc(UserCollect::getCreateTime));
        
        List<Recipe> recipes = new ArrayList<>();
        for (UserCollect collect : collects) {
            Recipe recipe = recipeService.getById(collect.getRecipeId());
            if (recipe != null && recipe.getStatus() == 1) {
                recipes.add(recipe);
            }
        }
        
        return recipes;
    }
}

