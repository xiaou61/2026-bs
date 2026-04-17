package com.xiaou.sport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.sport.common.Result;
import com.xiaou.sport.entity.FitnessPlan;
import com.xiaou.sport.service.FitnessPlanService;
import com.xiaou.sport.service.UserService;
import com.xiaou.sport.utils.PointRuleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plan")
public class FitnessPlanController {

    @Autowired
    private FitnessPlanService fitnessPlanService;

    @Autowired
    private UserService userService;

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
    public Result<FitnessPlan> getPlan(@PathVariable Long id, @RequestAttribute Long userId) {
        LambdaQueryWrapper<FitnessPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FitnessPlan::getId, id)
                .eq(FitnessPlan::getUserId, userId);
        FitnessPlan plan = fitnessPlanService.getOne(wrapper);
        if (plan == null) {
            return Result.error("计划不存在");
        }
        return Result.success(plan);
    }

    @PutMapping("/{id}/progress")
    @Transactional
    public Result<Void> updateProgress(@PathVariable Long id, @RequestAttribute Long userId, @RequestBody FitnessPlan plan) {
        LambdaQueryWrapper<FitnessPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FitnessPlan::getId, id)
                .eq(FitnessPlan::getUserId, userId);
        FitnessPlan existPlan = fitnessPlanService.getOne(wrapper);
        if (existPlan == null) {
            return Result.error("计划不存在");
        }

        int oldCompletedDays = existPlan.getCompletedDays() == null ? 0 : existPlan.getCompletedDays();
        int durationDays = existPlan.getDurationDays() == null ? 0 : existPlan.getDurationDays();
        int newCompletedDays = plan.getCompletedDays() == null ? oldCompletedDays : plan.getCompletedDays();
        newCompletedDays = Math.max(0, Math.min(newCompletedDays, durationDays));

        existPlan.setCompletedDays(newCompletedDays);
        existPlan.setStatus(newCompletedDays >= durationDays ? "completed" : "active");
        fitnessPlanService.updateById(existPlan);
        addUserPoints(userId, PointRuleUtil.calculatePlanProgressPoints(oldCompletedDays, newCompletedDays));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deletePlan(@PathVariable Long id, @RequestAttribute Long userId) {
        LambdaQueryWrapper<FitnessPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FitnessPlan::getId, id)
                .eq(FitnessPlan::getUserId, userId);
        if (fitnessPlanService.count(wrapper) == 0) {
            return Result.error("计划不存在");
        }
        fitnessPlanService.remove(wrapper);
        return Result.success();
    }

    private void addUserPoints(Long userId, int points) {
        if (points <= 0) {
            return;
        }
        var user = userService.getById(userId);
        if (user == null) {
            return;
        }
        int currentPoints = user.getPoints() == null ? 0 : user.getPoints();
        user.setPoints(currentPoints + points);
        userService.updateById(user);
    }
}
