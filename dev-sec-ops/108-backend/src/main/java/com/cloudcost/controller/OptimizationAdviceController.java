package com.cloudcost.controller;

import com.cloudcost.common.Result;
import com.cloudcost.entity.OptimizationAdvice;
import com.cloudcost.service.AuthService;
import com.cloudcost.service.OptimizationAdviceService;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/api/advice")
@RequiredArgsConstructor
public class OptimizationAdviceController {
    private final AuthService authService;
    private final OptimizationAdviceService service;

    @GetMapping("/page")
    public Result<PageInfo<OptimizationAdvice>> page(@RequestAttribute String role,
                                      @RequestParam(required = false) Integer pageNum,
                                      @RequestParam(required = false) Integer pageSize,
                                      @RequestParam(required = false) String keyword,
                                      @RequestParam(required = false) String status) {
        authService.assertAdminOrDevopsOrManager(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody OptimizationAdvice entity) {
        authService.assertAdminOrDevops(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody OptimizationAdvice entity) {
        authService.assertAdminOrDevops(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/accept/{id}")
    public Result<Void> accept(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrDevops(role);
        service.updateStatus(id, "ACCEPTED");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrDevops(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<Void> reject(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrManager(role);
        service.updateStatus(id, "REJECTED");
        return Result.success();
    }
}
