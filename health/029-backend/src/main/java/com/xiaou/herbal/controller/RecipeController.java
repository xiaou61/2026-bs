package com.xiaou.herbal.controller;

import com.xiaou.herbal.common.Result;
import com.xiaou.herbal.dto.RecipeCreateRequest;
import com.xiaou.herbal.entity.Recipe;
import com.xiaou.herbal.service.RecipeService;
import com.xiaou.herbal.util.UserContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@Validated
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/create")
    public Result<Void> createRecipe(@Validated @RequestBody RecipeCreateRequest request) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return Result.error(401, "未授权");
        }
        try {
            recipeService.createRecipe(userId, request);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<List<Recipe>> listRecipes(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            List<Recipe> recipes = recipeService.listPublishedRecipes(page, pageSize);
            return Result.success(recipes);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}/detail")
    public Result<Recipe> getRecipeDetail(@PathVariable Long id) {
        try {
            recipeService.increaseViews(id);
            Recipe recipe = recipeService.getRecipeDetail(id);
            return Result.success(recipe);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/search")
    public Result<List<Recipe>> searchRecipes(@RequestParam String keyword) {
        try {
            List<Recipe> recipes = recipeService.searchRecipes(keyword);
            return Result.success(recipes);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<Void> updateRecipe(
            @PathVariable Long id,
            @Validated @RequestBody RecipeCreateRequest request) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return Result.error(401, "未授权");
        }
        try {
            recipeService.updateRecipe(id, request);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteRecipe(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return Result.error(401, "未授权");
        }
        try {
            recipeService.deleteRecipe(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/{id}/collect")
    public Result<Void> collectRecipe(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return Result.error(401, "未授权");
        }
        try {
            recipeService.collectRecipe(userId, id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/my-recipes")
    public Result<List<Recipe>> getMyRecipes() {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return Result.error(401, "未授权");
        }
        try {
            List<Recipe> recipes = recipeService.getUserRecipes(userId);
            return Result.success(recipes);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
