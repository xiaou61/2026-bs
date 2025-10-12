package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.entity.Ingredient;
import com.xiaou.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/list")
    public Result<IPage<Ingredient>> getList(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(required = false) String category,
                                             @RequestParam(required = false) String name) {
        IPage<Ingredient> page = ingredientService.getIngredientList(pageNum, pageSize, category, name);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result<Ingredient> getDetail(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.getById(id);
        return Result.success(ingredient);
    }
}

