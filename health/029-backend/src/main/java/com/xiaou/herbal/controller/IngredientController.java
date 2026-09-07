package com.xiaou.herbal.controller;

import com.xiaou.herbal.common.Result;
import com.xiaou.herbal.entity.Ingredient;
import com.xiaou.herbal.entity.IngredientTaboo;
import com.xiaou.herbal.service.IngredientService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
@Validated
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/{id}/detail")
    public Result<Ingredient> getIngredientDetail(@PathVariable Long id) {
        try {
            Ingredient ingredient = ingredientService.getIngredientDetail(id);
            return Result.success(ingredient);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/category/{category}")
    public Result<List<Ingredient>> getByCategory(@PathVariable String category) {
        try {
            List<Ingredient> ingredients = ingredientService.listByCategory(category);
            return Result.success(ingredients);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/search")
    public Result<List<Ingredient>> searchIngredients(@RequestParam String keyword) {
        try {
            List<Ingredient> ingredients = ingredientService.searchIngredients(keyword);
            return Result.success(ingredients);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}/taboos")
    public Result<List<IngredientTaboo>> getTaboos(@PathVariable Long id) {
        try {
            List<IngredientTaboo> taboos = ingredientService.getTaboos(id);
            return Result.success(taboos);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}/tabooed-ingredients")
    public Result<List<Ingredient>> getTabooedIngredients(@PathVariable Long id) {
        try {
            List<Ingredient> ingredients = ingredientService.getTabooedIngredients(id);
            return Result.success(ingredients);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
