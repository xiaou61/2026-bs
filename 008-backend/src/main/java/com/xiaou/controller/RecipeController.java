package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.dto.RecipeQueryDTO;
import com.xiaou.entity.Recipe;
import com.xiaou.service.RecipeService;
import com.xiaou.service.UserCollectService;
import com.xiaou.vo.RecipeDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserCollectService userCollectService;

    @GetMapping("/list")
    public Result<IPage<Recipe>> getList(@ModelAttribute RecipeQueryDTO dto) {
        IPage<Recipe> page = recipeService.getRecipeList(dto);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result<RecipeDetailVO> getDetail(@PathVariable Long id,
                                            @RequestAttribute(value = "userId", required = false) Long userId) {
        recipeService.incrementViewCount(id);
        RecipeDetailVO detail = recipeService.getRecipeDetail(id, userId);
        return Result.success(detail);
    }

    @GetMapping("/search")
    public Result<List<Recipe>> search(@RequestParam String keyword) {
        List<Recipe> list = recipeService.searchRecipes(keyword);
        return Result.success(list);
    }

    @PostMapping("/collect/{id}")
    public Result<String> collect(@PathVariable Long id,
                                  @RequestAttribute("userId") Long userId) {
        userCollectService.collectRecipe(userId, id);
        return Result.success("收藏成功");
    }

    @DeleteMapping("/collect/{id}")
    public Result<String> cancelCollect(@PathVariable Long id,
                                        @RequestAttribute("userId") Long userId) {
        userCollectService.cancelCollect(userId, id);
        return Result.success("取消收藏成功");
    }

    @GetMapping("/collect/list")
    public Result<List<Recipe>> getCollectList(@RequestAttribute("userId") Long userId) {
        List<Recipe> list = userCollectService.getCollectList(userId);
        return Result.success(list);
    }
}

