package com.agriculture.controller;

import com.agriculture.common.Result;
import com.agriculture.entity.ProductionPlan;
import com.agriculture.service.PlanService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/plan")
public class PlanController {

    @Autowired
    private PlanService planService;

    @GetMapping("/page")
    public Result<Page<ProductionPlan>> getPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                                 @RequestParam(required = false) String planName,
                                                 @RequestParam(required = false) Integer status) {
        return Result.success(planService.getPage(pageNum, pageSize, planName, status));
    }

    @GetMapping("/{id}")
    public Result<ProductionPlan> getById(@PathVariable Long id) {
        return Result.success(planService.getById(id));
    }

    @PostMapping
    public Result<?> add(@RequestBody ProductionPlan plan, HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        plan.setCreatorId(userId);
        planService.save(plan);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody ProductionPlan plan) {
        planService.updateById(plan);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        planService.removeById(id);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        planService.updateStatus(id, body.get("status"));
        return Result.success();
    }
}
