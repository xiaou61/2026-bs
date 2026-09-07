package com.diet.management.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.diet.management.common.Result;
import com.diet.management.entity.DietRecord;
import com.diet.management.entity.NutritionGoal;
import com.diet.management.mapper.DietRecordMapper;
import com.diet.management.mapper.NutritionGoalMapper;
import com.diet.management.util.NutritionCalculator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "营养分析")
@RestController
@RequestMapping("/nutrition")
@RequiredArgsConstructor
public class NutritionController {

    private final DietRecordMapper dietRecordMapper;
    private final NutritionGoalMapper nutritionGoalMapper;

    @Operation(summary = "获取每日营养汇总")
    @GetMapping("/daily")
    public Result<Map<String, Object>> daily(@RequestParam(defaultValue = "2") Long userId,
                                             @RequestParam(required = false) LocalDate date) {
        LocalDate targetDate = date == null ? LocalDate.now() : date;
        LambdaQueryWrapper<DietRecord> recordWrapper = new LambdaQueryWrapper<>();
        recordWrapper.eq(DietRecord::getUserId, userId)
                .eq(DietRecord::getEatDate, targetDate);
        List<DietRecord> records = dietRecordMapper.selectList(recordWrapper);

        BigDecimal calorie = sum(records, DietRecord::getCalorie);
        BigDecimal protein = sum(records, DietRecord::getProtein);
        BigDecimal fat = sum(records, DietRecord::getFat);
        BigDecimal carbohydrate = sum(records, DietRecord::getCarbohydrate);

        NutritionGoal goal = nutritionGoalMapper.selectOne(new LambdaQueryWrapper<NutritionGoal>()
                .eq(NutritionGoal::getUserId, userId)
                .eq(NutritionGoal::getIsActive, 1)
                .orderByDesc(NutritionGoal::getStartDate)
                .last("LIMIT 1"));

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("date", targetDate);
        result.put("calorie", calorie);
        result.put("protein", protein);
        result.put("fat", fat);
        result.put("carbohydrate", carbohydrate);
        result.put("goal", goal);
        if (goal != null) {
            result.put("calorieAchievementRate",
                    NutritionCalculator.calculateAchievementRate(calorie, goal.getTargetCalorie()));
        }
        return Result.success(result);
    }

    private BigDecimal sum(List<DietRecord> records, NutrientGetter getter) {
        return records.stream()
                .map(getter::get)
                .filter(value -> value != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @FunctionalInterface
    private interface NutrientGetter {
        BigDecimal get(DietRecord record);
    }
}
