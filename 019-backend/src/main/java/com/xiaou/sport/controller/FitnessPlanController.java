package com.xiaou.sport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.sport.common.Result;
import com.xiaou.sport.entity.FitnessPlan;
import com.xiaou.sport.service.FitnessPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plan")
public class FitnessPlanController {

    @Autowired
    private FitnessPlanService fitnessPlanService;

    @PostMapping("/create")
    public Result<Void> createPlan(@RequestAttribute Long userId, @RequestBody FitnessPlan plan) {
        plan.setUserId(userId);
        plan.setCompletedDays(0);
        plan.setStatus("active");
        fitnessPlanService.save(plan);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<Page<FitnessPlan>> listPlans(@RequestAttribute Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<FitnessPlan> pageInfo = new Page<>(page, size);
        LambdaQueryWrapper<FitnessPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FitnessPlan::getUserId, userId)
                .orderByDesc(FitnessPlan::getCreateTime);
        fitnessPlanService.page(pageInfo, wrapper);
        return Result.success(pageInfo);
    }

    @GetMapping("/{id}")
    public Result<FitnessPlan> getPlan(@PathVariable Long id) {
        FitnessPlan plan = fitnessPlanService.getById(id);
        return Result.success(plan);
    }

    @PutMapping("/{id}/progress")
    public Result<Void> updateProgress(@PathVariable Long id, @RequestBody FitnessPlan plan) {
        FitnessPlan existPlan = fitnessPlanService.getById(id);
        existPlan.setCompletedDays(plan.getCompletedDays());
        if (existPlan.getCompletedDays().equals(existPlan.getDurationDays())) {
            existPlan.setStatus("completed");
        }
        fitnessPlanService.updateById(existPlan);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deletePlan(@PathVariable Long id) {
        fitnessPlanService.removeById(id);
        return Result.success();
    }
}
