package com.classic.controller;

import com.classic.common.BusinessException;
import com.classic.common.Result;
import com.classic.entity.MealPlan;
import com.classic.service.MealPlanService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/plan")
public class MealPlanController {

    @Resource
    private MealPlanService mealPlanService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(mealPlanService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) String suitableConstitution,
                          @RequestParam(required = false) Integer status) {
        return Result.success(mealPlanService.page(pageNum, pageSize, name, suitableConstitution, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody MealPlan mealPlan, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        mealPlanService.save(mealPlan);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody MealPlan mealPlan, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        mealPlanService.save(mealPlan);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        mealPlanService.deleteById(id);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
