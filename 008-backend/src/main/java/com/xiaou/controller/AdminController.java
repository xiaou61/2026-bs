package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.dto.RecipeCreateDTO;
import com.xiaou.entity.Ingredient;
import com.xiaou.entity.Recipe;
import com.xiaou.entity.User;
import com.xiaou.service.AdminService;
import com.xiaou.service.IngredientService;
import com.xiaou.service.RecipeService;
import com.xiaou.service.UserService;
import com.xiaou.vo.StatsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/recipe/list")
    public Result<IPage<Recipe>> getRecipeList(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Recipe> page = new Page<>(pageNum, pageSize);
        IPage<Recipe> result = recipeService.page(page, new LambdaQueryWrapper<Recipe>()
                .orderByDesc(Recipe::getCreateTime));
        return Result.success(result);
    }

    @PostMapping("/recipe")
    public Result<String> createRecipe(@RequestBody RecipeCreateDTO dto) {
        recipeService.createRecipe(dto);
        return Result.success("创建成功");
    }

    @PutMapping("/recipe/{id}")
    public Result<String> updateRecipe(@PathVariable Long id,
                                       @RequestBody RecipeCreateDTO dto) {
        recipeService.updateRecipe(id, dto);
        return Result.success("更新成功");
    }

    @DeleteMapping("/recipe/{id}")
    public Result<String> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return Result.success("删除成功");
    }

    @GetMapping("/ingredient/list")
    public Result<IPage<Ingredient>> getIngredientList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Ingredient> page = new Page<>(pageNum, pageSize);
        IPage<Ingredient> result = ingredientService.page(page, new LambdaQueryWrapper<Ingredient>()
                .orderByDesc(Ingredient::getCreateTime));
        return Result.success(result);
    }

    @PostMapping("/ingredient")
    public Result<String> createIngredient(@RequestBody Ingredient ingredient) {
        ingredientService.save(ingredient);
        return Result.success("创建成功");
    }

    @PutMapping("/ingredient/{id}")
    public Result<String> updateIngredient(@PathVariable Long id,
                                           @RequestBody Ingredient ingredient) {
        ingredient.setId(id);
        ingredientService.updateById(ingredient);
        return Result.success("更新成功");
    }

    @DeleteMapping("/ingredient/{id}")
    public Result<String> deleteIngredient(@PathVariable Long id) {
        ingredientService.removeById(id);
        return Result.success("删除成功");
    }

    @GetMapping("/user/list")
    public Result<IPage<User>> getUserList(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        IPage<User> result = userService.page(page, new LambdaQueryWrapper<User>()
                .eq(User::getRole, "USER")
                .orderByDesc(User::getCreateTime));
        
        result.getRecords().forEach(user -> user.setPassword(null));
        
        return Result.success(result);
    }

    @PutMapping("/user/{id}/status")
    public Result<String> updateUserStatus(@PathVariable Long id,
                                           @RequestParam Integer status) {
        User user = userService.getById(id);
        user.setStatus(status);
        userService.updateById(user);
        return Result.success("更新成功");
    }

    @GetMapping("/stats")
    public Result<StatsVO> getStats() {
        StatsVO stats = adminService.getStats();
        return Result.success(stats);
    }
}

