package com.enrollment.controller;

import com.enrollment.common.Result;
import com.enrollment.entity.EnrollmentPlan;
import com.enrollment.service.EnrollmentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plan")
public class PlanController {

    @Autowired
    private EnrollmentPlanService planService;

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) Integer year,
                             @RequestParam(required = false) Long majorId) {
        return Result.success(planService.getPage(pageNum, pageSize, year, majorId));
    }

    @GetMapping("/list/{year}")
    public Result<?> getByYear(@PathVariable Integer year) {
        return Result.success(planService.getByYear(year));
    }

    @PostMapping
    public Result<?> add(@RequestBody EnrollmentPlan plan) {
        planService.add(plan);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody EnrollmentPlan plan) {
        planService.update(plan);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        planService.delete(id);
        return Result.success();
    }

    @PutMapping("/publish/{id}")
    public Result<?> publish(@PathVariable Long id) {
        planService.publish(id);
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<?> close(@PathVariable Long id) {
        planService.close(id);
        return Result.success();
    }
}
