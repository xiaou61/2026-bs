package com.diet.management.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diet.management.common.Result;
import com.diet.management.entity.Recipe;
import com.diet.management.mapper.RecipeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "食谱推荐")
@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeMapper recipeMapper;

    @Operation(summary = "分页查询食谱")
    @GetMapping("/page")
    public Result<Page<Recipe>> page(@RequestParam(defaultValue = "1") Integer current,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     @RequestParam(required = false) String category,
                                     @RequestParam(required = false) String keyword) {
        Page<Recipe> page = new Page<>(current, size);
        LambdaQueryWrapper<Recipe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(category != null && !category.isBlank(), Recipe::getCategory, category)
                .like(keyword != null && !keyword.isBlank(), Recipe::getName, keyword)
                .orderByDesc(Recipe::getCreateTime);
        return Result.success(recipeMapper.selectPage(page, wrapper));
    }

    @Operation(summary = "获取食谱详情")
    @GetMapping("/{id}")
    public Result<Recipe> getById(@PathVariable Long id) {
        Recipe recipe = recipeMapper.selectById(id);
        if (recipe == null) {
            throw new IllegalArgumentException("食谱不存在");
        }
        recipe.setViewCount((recipe.getViewCount() == null ? 0 : recipe.getViewCount()) + 1);
        recipeMapper.updateById(recipe);
        return Result.success(recipe);
    }
}
