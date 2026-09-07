package com.xiaou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.dto.RecipeCreateDTO;
import com.xiaou.dto.RecipeIngredientDTO;
import com.xiaou.dto.RecipeQueryDTO;
import com.xiaou.dto.RecipeStepDTO;
import com.xiaou.entity.*;
import com.xiaou.exception.BusinessException;
import com.xiaou.mapper.RecipeMapper;
import com.xiaou.service.*;
import com.xiaou.vo.RecipeDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImpl extends ServiceImpl<RecipeMapper, Recipe> implements RecipeService {

    @Autowired
    private RecipeIngredientService recipeIngredientService;
    
    @Autowired
    private RecipeStepService recipeStepService;
    
    @Lazy
    @Autowired
    private UserCollectService userCollectService;
    
    @Autowired
    private IngredientService ingredientService;

    @Override
    public IPage<Recipe> getRecipeList(RecipeQueryDTO dto) {
        Page<Recipe> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        LambdaQueryWrapper<Recipe> wrapper = new LambdaQueryWrapper<>();
        
        wrapper.eq(Recipe::getStatus, 1);
        
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            wrapper.like(Recipe::getName, dto.getName());
        }
        
        if (dto.getCuisineType() != null && !dto.getCuisineType().isEmpty()) {
            wrapper.eq(Recipe::getCuisineType, dto.getCuisineType());
        }
        
        if (dto.getDifficulty() != null) {
            wrapper.eq(Recipe::getDifficulty, dto.getDifficulty());
        }
        
        if (dto.getMaxCookingTime() != null) {
            wrapper.le(Recipe::getCookingTime, dto.getMaxCookingTime());
        }
        
        wrapper.orderByDesc(Recipe::getCreateTime);
        
        return page(page, wrapper);
    }

    @Override
    public RecipeDetailVO getRecipeDetail(Long recipeId, Long userId) {
        Recipe recipe = getById(recipeId);
        if (recipe == null) {
            throw new BusinessException("菜谱不存在");
        }
        
        RecipeDetailVO vo = new RecipeDetailVO();
        BeanUtil.copyProperties(recipe, vo);
        
        List<RecipeIngredient> recipeIngredients = recipeIngredientService.list(
                new LambdaQueryWrapper<RecipeIngredient>().eq(RecipeIngredient::getRecipeId, recipeId));
        
        List<RecipeIngredientDTO> ingredientDTOs = new ArrayList<>();
        for (RecipeIngredient ri : recipeIngredients) {
            RecipeIngredientDTO dto = new RecipeIngredientDTO();
            BeanUtil.copyProperties(ri, dto);
            
            Ingredient ingredient = ingredientService.getById(ri.getIngredientId());
            if (ingredient != null) {
                dto.setIngredientName(ingredient.getName());
            }
            
            ingredientDTOs.add(dto);
        }
        vo.setIngredients(ingredientDTOs);
        
        List<RecipeStep> recipeSteps = recipeStepService.list(
                new LambdaQueryWrapper<RecipeStep>()
                        .eq(RecipeStep::getRecipeId, recipeId)
                        .orderByAsc(RecipeStep::getStepNumber));
        
        List<RecipeStepDTO> stepDTOs = new ArrayList<>();
        for (RecipeStep rs : recipeSteps) {
            RecipeStepDTO dto = new RecipeStepDTO();
            BeanUtil.copyProperties(rs, dto);
            stepDTOs.add(dto);
        }
        vo.setSteps(stepDTOs);
        
        if (userId != null) {
            Long count = userCollectService.count(new LambdaQueryWrapper<UserCollect>()
                    .eq(UserCollect::getUserId, userId)
                    .eq(UserCollect::getRecipeId, recipeId));
            vo.setIsCollected(count > 0);
        } else {
            vo.setIsCollected(false);
        }
        
        return vo;
    }

    @Override
    public List<Recipe> searchRecipes(String keyword) {
        return list(new LambdaQueryWrapper<Recipe>()
                .eq(Recipe::getStatus, 1)
                .and(w -> w.like(Recipe::getName, keyword)
                        .or()
                        .like(Recipe::getDescription, keyword))
                .orderByDesc(Recipe::getRatingScore));
    }

    @Override
    @Transactional
    public void createRecipe(RecipeCreateDTO dto) {
        Recipe recipe = new Recipe();
        BeanUtil.copyProperties(dto, recipe);
        recipe.setStatus(1);
        recipe.setViewCount(0);
        recipe.setCollectCount(0);
        recipe.setRatingScore(new BigDecimal("5.00"));
        recipe.setRatingCount(0);
        
        BigDecimal totalCalories = BigDecimal.ZERO;
        if (dto.getIngredients() != null) {
            for (RecipeIngredientDTO ingredientDTO : dto.getIngredients()) {
                Ingredient ingredient = ingredientService.getById(ingredientDTO.getIngredientId());
                if (ingredient != null) {
                    BigDecimal calories = ingredient.getCalories()
                            .multiply(ingredientDTO.getQuantity())
                            .divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
                    totalCalories = totalCalories.add(calories);
                }
            }
        }
        recipe.setTotalCalories(totalCalories);
        
        save(recipe);
        
        if (dto.getIngredients() != null) {
            for (RecipeIngredientDTO ingredientDTO : dto.getIngredients()) {
                RecipeIngredient ri = new RecipeIngredient();
                ri.setRecipeId(recipe.getId());
                ri.setIngredientId(ingredientDTO.getIngredientId());
                ri.setQuantity(ingredientDTO.getQuantity());
                ri.setUnit(ingredientDTO.getUnit());
                ri.setIsRequired(ingredientDTO.getIsRequired());
                recipeIngredientService.save(ri);
            }
        }
        
        if (dto.getSteps() != null) {
            for (RecipeStepDTO stepDTO : dto.getSteps()) {
                RecipeStep step = new RecipeStep();
                step.setRecipeId(recipe.getId());
                BeanUtil.copyProperties(stepDTO, step);
                recipeStepService.save(step);
            }
        }
    }

    @Override
    @Transactional
    public void updateRecipe(Long recipeId, RecipeCreateDTO dto) {
        Recipe recipe = getById(recipeId);
        if (recipe == null) {
            throw new BusinessException("菜谱不存在");
        }
        
        BeanUtil.copyProperties(dto, recipe);
        
        BigDecimal totalCalories = BigDecimal.ZERO;
        if (dto.getIngredients() != null) {
            for (RecipeIngredientDTO ingredientDTO : dto.getIngredients()) {
                Ingredient ingredient = ingredientService.getById(ingredientDTO.getIngredientId());
                if (ingredient != null) {
                    BigDecimal calories = ingredient.getCalories()
                            .multiply(ingredientDTO.getQuantity())
                            .divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
                    totalCalories = totalCalories.add(calories);
                }
            }
        }
        recipe.setTotalCalories(totalCalories);
        
        updateById(recipe);
        
        recipeIngredientService.remove(new LambdaQueryWrapper<RecipeIngredient>()
                .eq(RecipeIngredient::getRecipeId, recipeId));
        
        if (dto.getIngredients() != null) {
            for (RecipeIngredientDTO ingredientDTO : dto.getIngredients()) {
                RecipeIngredient ri = new RecipeIngredient();
                ri.setRecipeId(recipeId);
                ri.setIngredientId(ingredientDTO.getIngredientId());
                ri.setQuantity(ingredientDTO.getQuantity());
                ri.setUnit(ingredientDTO.getUnit());
                ri.setIsRequired(ingredientDTO.getIsRequired());
                recipeIngredientService.save(ri);
            }
        }
        
        recipeStepService.remove(new LambdaQueryWrapper<RecipeStep>()
                .eq(RecipeStep::getRecipeId, recipeId));
        
        if (dto.getSteps() != null) {
            for (RecipeStepDTO stepDTO : dto.getSteps()) {
                RecipeStep step = new RecipeStep();
                step.setRecipeId(recipeId);
                BeanUtil.copyProperties(stepDTO, step);
                recipeStepService.save(step);
            }
        }
    }

    @Override
    @Transactional
    public void deleteRecipe(Long recipeId) {
        Recipe recipe = getById(recipeId);
        if (recipe == null) {
            throw new BusinessException("菜谱不存在");
        }
        
        removeById(recipeId);
        
        recipeIngredientService.remove(new LambdaQueryWrapper<RecipeIngredient>()
                .eq(RecipeIngredient::getRecipeId, recipeId));
        
        recipeStepService.remove(new LambdaQueryWrapper<RecipeStep>()
                .eq(RecipeStep::getRecipeId, recipeId));
    }

    @Override
    public void incrementViewCount(Long recipeId) {
        Recipe recipe = getById(recipeId);
        if (recipe != null) {
            recipe.setViewCount(recipe.getViewCount() + 1);
            updateById(recipe);
        }
    }
}

