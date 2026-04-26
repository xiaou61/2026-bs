package com.diet.management.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.diet.management.common.Result;
import com.diet.management.entity.NutritionGoal;
import com.diet.management.mapper.NutritionGoalMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "营养目标")
@RestController
@RequestMapping("/goal")
@RequiredArgsConstructor
public class NutritionGoalController {

    private final NutritionGoalMapper nutritionGoalMapper;

    @Operation(summary = "查询用户营养目标")
    @GetMapping("/list")
    public Result<List<NutritionGoal>> list(@RequestParam(defaultValue = "2") Long userId) {
        LambdaQueryWrapper<NutritionGoal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NutritionGoal::getUserId, userId)
                .orderByDesc(NutritionGoal::getStartDate);
        return Result.success(nutritionGoalMapper.selectList(wrapper));
    }

    @Operation(summary = "查询当前生效目标")
    @GetMapping("/active")
    public Result<NutritionGoal> active(@RequestParam(defaultValue = "2") Long userId) {
        LambdaQueryWrapper<NutritionGoal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NutritionGoal::getUserId, userId)
                .eq(NutritionGoal::getIsActive, 1)
                .orderByDesc(NutritionGoal::getStartDate)
                .last("LIMIT 1");
        return Result.success(nutritionGoalMapper.selectOne(wrapper));
    }

    @Operation(summary = "保存营养目标")
    @PostMapping
    public Result<String> save(@RequestBody NutritionGoal goal) {
        if (goal.getUserId() == null) {
            goal.setUserId(2L);
        }
        if (goal.getStartDate() == null) {
            goal.setStartDate(LocalDate.now());
        }
        if (goal.getIsActive() == null) {
            goal.setIsActive(1);
        }
        nutritionGoalMapper.insert(goal);
        return Result.success("保存成功");
    }
}
