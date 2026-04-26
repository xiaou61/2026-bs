package com.diet.management.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.diet.management.common.Result;
import com.diet.management.entity.HealthData;
import com.diet.management.entity.User;
import com.diet.management.mapper.HealthDataMapper;
import com.diet.management.mapper.UserMapper;
import com.diet.management.util.NutritionCalculator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "健康数据")
@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthDataController {

    private final HealthDataMapper healthDataMapper;
    private final UserMapper userMapper;

    @Operation(summary = "查询用户健康数据")
    @GetMapping("/list")
    public Result<List<HealthData>> list(@RequestParam(defaultValue = "2") Long userId) {
        LambdaQueryWrapper<HealthData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HealthData::getUserId, userId)
                .orderByDesc(HealthData::getRecordDate);
        return Result.success(healthDataMapper.selectList(wrapper));
    }

    @Operation(summary = "获取最近一条健康数据")
    @GetMapping("/latest")
    public Result<HealthData> latest(@RequestParam(defaultValue = "2") Long userId) {
        LambdaQueryWrapper<HealthData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HealthData::getUserId, userId)
                .orderByDesc(HealthData::getRecordDate)
                .last("LIMIT 1");
        return Result.success(healthDataMapper.selectOne(wrapper));
    }

    @Operation(summary = "保存健康数据")
    @PostMapping
    public Result<String> save(@RequestBody HealthData healthData) {
        if (healthData.getUserId() == null) {
            healthData.setUserId(2L);
        }
        if (healthData.getRecordDate() == null) {
            healthData.setRecordDate(LocalDate.now());
        }
        User user = userMapper.selectById(healthData.getUserId());
        if (healthData.getBmi() == null && user != null && user.getHeight() != null && healthData.getWeight() != null) {
            healthData.setBmi(NutritionCalculator.calculateBMI(healthData.getWeight(), user.getHeight()));
        }
        healthDataMapper.insert(healthData);
        return Result.success("保存成功");
    }
}
