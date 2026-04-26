package com.diet.management.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diet.management.common.Result;
import com.diet.management.entity.DietRecord;
import com.diet.management.entity.Food;
import com.diet.management.mapper.DietRecordMapper;
import com.diet.management.mapper.FoodMapper;
import com.diet.management.util.NutritionCalculator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Tag(name = "饮食记录")
@RestController
@RequestMapping("/diet-record")
@RequiredArgsConstructor
public class DietRecordController {

    private final DietRecordMapper dietRecordMapper;
    private final FoodMapper foodMapper;

    @Operation(summary = "分页查询饮食记录")
    @GetMapping("/page")
    public Result<Page<DietRecord>> page(@RequestParam(defaultValue = "1") Integer current,
                                         @RequestParam(defaultValue = "10") Integer size,
                                         @RequestParam(required = false) Long userId,
                                         @RequestParam(required = false) LocalDate eatDate) {
        Page<DietRecord> page = new Page<>(current, size);
        LambdaQueryWrapper<DietRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(userId != null, DietRecord::getUserId, userId)
                .eq(eatDate != null, DietRecord::getEatDate, eatDate)
                .orderByDesc(DietRecord::getEatDate)
                .orderByDesc(DietRecord::getCreateTime);
        return Result.success(dietRecordMapper.selectPage(page, wrapper));
    }

    @Operation(summary = "添加饮食记录")
    @PostMapping
    public Result<String> add(@RequestBody DietRecord record) {
        if (record.getUserId() == null) {
            record.setUserId(2L);
        }
        if (record.getEatDate() == null) {
            record.setEatDate(LocalDate.now());
        }
        if (record.getFoodId() != null) {
            Food food = foodMapper.selectById(record.getFoodId());
            if (food != null) {
                record.setFoodName(food.getName());
                fillNutrition(record, food);
            }
        }
        dietRecordMapper.insert(record);
        return Result.success("添加成功");
    }

    @Operation(summary = "删除饮食记录")
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        dietRecordMapper.deleteById(id);
        return Result.success("删除成功");
    }

    private void fillNutrition(DietRecord record, Food food) {
        BigDecimal weight = record.getWeight();
        record.setCalorie(NutritionCalculator.calculateNutrient(food.getCalorie(), weight));
        record.setProtein(NutritionCalculator.calculateNutrient(food.getProtein(), weight));
        record.setFat(NutritionCalculator.calculateNutrient(food.getFat(), weight));
        record.setCarbohydrate(NutritionCalculator.calculateNutrient(food.getCarbohydrate(), weight));
        record.setFiber(NutritionCalculator.calculateNutrient(food.getFiber(), weight));
        record.setSodium(NutritionCalculator.calculateNutrient(food.getSodium(), weight));
    }
}
