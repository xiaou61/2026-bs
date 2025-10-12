package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.service.NutritionService;
import com.xiaou.vo.NutritionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nutrition")
public class NutritionController {

    @Autowired
    private NutritionService nutritionService;

    @GetMapping("/recipe/{id}")
    public Result<NutritionVO> getRecipeNutrition(@PathVariable Long id) {
        NutritionVO vo = nutritionService.getRecipeNutrition(id);
        return Result.success(vo);
    }

    @GetMapping("/daily")
    public Result<NutritionVO> getDailyNutrition(@RequestAttribute("userId") Long userId) {
        NutritionVO vo = nutritionService.getDailyNutrition(userId);
        return Result.success(vo);
    }
}

