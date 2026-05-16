package com.cloudmonitor.controller;

import com.cloudmonitor.common.Result;
import com.cloudmonitor.entity.CapacityPlan;
import com.cloudmonitor.service.AuthService;
import com.cloudmonitor.service.CapacityPlanService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/capacity")
@RequiredArgsConstructor
public class CapacityPlanController {
    private final CapacityPlanService service;
    private final AuthService authService;

    @GetMapping("/page")
    public Result<IPage<CapacityPlan>> page(@RequestAttribute String role,
                                     @RequestParam(required = false) Integer pageNum,
                                     @RequestParam(required = false) Integer pageSize,
                                     @RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }


    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody CapacityPlan entity) {
        authService.assertAdminOrOpsOrSre(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody CapacityPlan entity) {
        authService.assertAdminOrOpsOrSre(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrSreOrManager(role);
        service.delete(id);
        return Result.success();
    }



    @PutMapping("/publish/{id}")
    public Result<Void> publish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrSreOrManager(role);
        service.updateStatus(id, "PUBLISHED");
        return Result.success();
    }


    @PutMapping("/archive/{id}")
    public Result<Void> archive(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrSreOrManager(role);
        service.updateStatus(id, "ARCHIVED");
        return Result.success();
    }

}
