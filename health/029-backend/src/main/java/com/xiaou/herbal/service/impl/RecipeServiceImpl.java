package com.xiaou.herbal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.herbal.common.Constants;
import com.xiaou.herbal.dto.RecipeCreateRequest;
import com.xiaou.herbal.entity.Collection;
import com.xiaou.herbal.entity.Recipe;
import com.xiaou.herbal.entity.RecipeIngredient;
import com.xiaou.herbal.mapper.CollectionMapper;
import com.xiaou.herbal.mapper.RecipeIngredientMapper;
import com.xiaou.herbal.mapper.RecipeMapper;
import com.xiaou.herbal.service.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipeServiceImpl extends ServiceImpl<RecipeMapper, Recipe> implements RecipeService {

    private final RecipeIngredientMapper recipeIngredientMapper;
    private final CollectionMapper collectionMapper;

    public RecipeServiceImpl(RecipeIngredientMapper recipeIngredientMapper, CollectionMapper collectionMapper) {
        this.recipeIngredientMapper = recipeIngredientMapper;
        this.collectionMapper = collectionMapper;
    }

    @Override
    @Transactional
    public void createRecipe(Long userId, RecipeCreateRequest request) {
        Recipe recipe = Recipe.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .authorId(userId)
                .coverImage(request.getCoverImage())
                .efficacy(request.getEfficacy())
                .nutrition(request.getNutrition())
                .difficulty(request.getDifficulty())
                .cookTime(request.getCookTime())
                .servings(request.getServings())
                .applicablePeople(request.getApplicablePeople())
                .seasons(request.getSeasons())
                .status(Constants.RecipeStatus.DRAFT)
                .views(0)
                .likes(0)
                .collects(0)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        baseMapper.insert(recipe);

        if (request.getIngredients() != null && !request.getIngredients().isEmpty()) {
            for (RecipeCreateRequest.RecipeIngredientRequest ingredient : request.getIngredients()) {
                RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                        .recipeId(recipe.getId())
                        .ingredientId(ingredient.getIngredientId())
                        .quantity(new BigDecimal(ingredient.getQuantity()))
                        .unit(ingredient.getUnit())
                        .createTime(LocalDateTime.now())
                        .build();
                recipeIngredientMapper.insert(recipeIngredient);
            }
        }
    }

    @Override
    public Recipe getRecipeDetail(Long recipeId) {
        return baseMapper.selectById(recipeId);
    }

    @Override
    public List<Recipe> listPublishedRecipes(Integer page, Integer pageSize) {
        LambdaQueryWrapper<Recipe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Recipe::getStatus, Constants.RecipeStatus.PUBLISHED)
                .orderByDesc(Recipe::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<Recipe> searchRecipes(String keyword) {
        LambdaQueryWrapper<Recipe> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Recipe::getTitle, keyword)
                .or()
                .like(Recipe::getDescription, keyword)
                .eq(Recipe::getStatus, Constants.RecipeStatus.PUBLISHED);
        return baseMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public void updateRecipe(Long recipeId, RecipeCreateRequest request) {
        Recipe recipe = Recipe.builder()
                .id(recipeId)
                .title(request.getTitle())
                .description(request.getDescription())
                .coverImage(request.getCoverImage())
                .efficacy(request.getEfficacy())
                .nutrition(request.getNutrition())
                .difficulty(request.getDifficulty())
                .cookTime(request.getCookTime())
                .servings(request.getServings())
                .applicablePeople(request.getApplicablePeople())
                .seasons(request.getSeasons())
                .updateTime(LocalDateTime.now())
                .build();

        baseMapper.updateById(recipe);

        if (request.getIngredients() != null && !request.getIngredients().isEmpty()) {
            LambdaQueryWrapper<RecipeIngredient> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(RecipeIngredient::getRecipeId, recipeId);
            recipeIngredientMapper.delete(wrapper);

            for (RecipeCreateRequest.RecipeIngredientRequest ingredient : request.getIngredients()) {
                RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                        .recipeId(recipeId)
                        .ingredientId(ingredient.getIngredientId())
                        .quantity(new BigDecimal(ingredient.getQuantity()))
                        .unit(ingredient.getUnit())
                        .createTime(LocalDateTime.now())
                        .build();
                recipeIngredientMapper.insert(recipeIngredient);
            }
        }
    }

    @Override
    @Transactional
    public void deleteRecipe(Long recipeId) {
        baseMapper.deleteById(recipeId);
        LambdaQueryWrapper<RecipeIngredient> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RecipeIngredient::getRecipeId, recipeId);
        recipeIngredientMapper.delete(wrapper);
    }

    @Override
    public List<Recipe> getUserRecipes(Long userId) {
        LambdaQueryWrapper<Recipe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Recipe::getAuthorId, userId).orderByDesc(Recipe::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public void increaseViews(Long recipeId) {
        Recipe recipe = baseMapper.selectById(recipeId);
        recipe.setViews(recipe.getViews() + 1);
        baseMapper.updateById(recipe);
    }

    @Override
    @Transactional
    public void collectRecipe(Long userId, Long recipeId) {
        if (!isCollected(userId, recipeId)) {
            Collection collection = Collection.builder()
                    .userId(userId)
                    .targetType(Constants.CollectionTargetType.RECIPE)
                    .targetId(recipeId)
                    .createTime(LocalDateTime.now())
                    .build();
            collectionMapper.insert(collection);

            Recipe recipe = baseMapper.selectById(recipeId);
            recipe.setCollects(recipe.getCollects() + 1);
            baseMapper.updateById(recipe);
        }
    }

    @Override
    public boolean isCollected(Long userId, Long recipeId) {
        LambdaQueryWrapper<Collection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collection::getUserId, userId)
                .eq(Collection::getTargetType, Constants.CollectionTargetType.RECIPE)
                .eq(Collection::getTargetId, recipeId);
        return collectionMapper.selectCount(wrapper) > 0;
    }
}
