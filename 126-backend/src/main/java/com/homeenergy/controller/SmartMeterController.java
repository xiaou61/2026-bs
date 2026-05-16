package com.homeenergy.controller;

import com.github.pagehelper.PageInfo;
import com.homeenergy.common.Result;
import com.homeenergy.entity.SmartMeter;
import com.homeenergy.service.AuthService;
import com.homeenergy.service.SmartMeterService;
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
@RequestMapping("/api/meter")
@RequiredArgsConstructor
public class SmartMeterController {
    private final AuthService authService;
    private final SmartMeterService service;

    @GetMapping("/page")
    public Result<PageInfo<SmartMeter>> page(@RequestAttribute String role,
                                             @RequestParam(required = false) Integer pageNum,
                                             @RequestParam(required = false) Integer pageSize,
                                             @RequestParam(required = false) String keyword,
                                             @RequestParam(required = false) String status) {
        authService.assertAdminOrResidentOrAnalystOrMaintainer(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody SmartMeter entity) {
        authService.assertAdminOrResident(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody SmartMeter entity) {
        authService.assertAdminOrResidentOrMaintainer(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/online/{id}")
    public Result<Void> online(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrMaintainer(role);
        service.updateStatus(id, "NORMAL");
        return Result.success();
    }

    @PutMapping("/warn/{id}")
    public Result<Void> warn(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrMaintainer(role);
        service.updateStatus(id, "WARNING");
        return Result.success();
    }
}
