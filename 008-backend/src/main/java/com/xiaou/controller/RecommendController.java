package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Recipe;
import com.xiaou.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    @GetMapping("/today")
    public Result<List<Recipe>> getTodayRecommend(@RequestAttribute("userId") Long userId) {
        List<Recipe> list = recommendService.getTodayRecommend(userId);
        return Result.success(list);
    }

    @GetMapping("/by-ingredient")
    public Result<List<Recipe>> getByIngredient(@RequestAttribute("userId") Long userId) {
        List<Recipe> list = recommendService.getByIngredient(userId);
        return Result.success(list);
    }

    @GetMapping("/hot")
    public Result<List<Recipe>> getHot() {
        List<Recipe> list = recommendService.getHotRecipes();
        return Result.success(list);
    }

    @GetMapping("/easy")
    public Result<List<Recipe>> getEasy() {
        List<Recipe> list = recommendService.getEasyRecipes();
        return Result.success(list);
    }
}

