package com.diet.management.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diet.management.common.Result;
import com.diet.management.entity.Food;
import com.diet.management.mapper.FoodMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 食物管理控制器
 */
@Tag(name = "食物管理")
@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
public class FoodController {
    
    private final FoodMapper foodMapper;
    
    @Operation(summary = "分页查询食物")
    @GetMapping("/page")
    public Result<Page<Food>> page(@RequestParam(defaultValue = "1") Integer current,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     @RequestParam(required = false) String name,
                                     @RequestParam(required = false) String category) {
        Page<Food> page = new Page<>(current, size);
        LambdaQueryWrapper<Food> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, Food::getName, name)
               .eq(category != null, Food::getCategory, category)
               .orderByDesc(Food::getCreateTime);
        return Result.success(foodMapper.selectPage(page, wrapper));
    }
    
    @Operation(summary = "添加食物")
    @PostMapping
    public Result<String> add(@RequestBody Food food) {
        foodMapper.insert(food);
        return Result.success("添加成功");
    }
    
    @Operation(summary = "获取食物详情")
    @GetMapping("/{id}")
    public Result<Food> getById(@PathVariable Long id) {
        return Result.success(foodMapper.selectById(id));
    }
}
