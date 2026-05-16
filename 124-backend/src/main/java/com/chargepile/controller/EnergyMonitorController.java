package com.chargepile.controller;

import com.github.pagehelper.PageInfo;
import com.chargepile.common.Result;
import com.chargepile.entity.EnergyMonitor;
import com.chargepile.service.AuthService;
import com.chargepile.service.EnergyMonitorService;
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
@RequestMapping("/api/energy")
@RequiredArgsConstructor
public class EnergyMonitorController {
    private final AuthService authService;
    private final EnergyMonitorService service;

    @GetMapping("/page")
    public Result<PageInfo<EnergyMonitor>> page(@RequestAttribute String role,
                                                @RequestParam(required = false) Integer pageNum,
                                                @RequestParam(required = false) Integer pageSize,
                                                @RequestParam(required = false) String keyword,
                                                @RequestParam(required = false) String status) {
        authService.assertAdminOrOperatorOrMaintainer(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody EnergyMonitor entity) {
        authService.assertAdminOrOperator(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody EnergyMonitor entity) {
        authService.assertAdminOrOperator(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/warn/{id}")
    public Result<Void> warn(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrOperatorOrMaintainer(role);
        service.updateStatus(id, "WARNING");
        return Result.success();
    }

    @PutMapping("/recover/{id}")
    public Result<Void> recover(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrOperatorOrMaintainer(role);
        service.updateStatus(id, "NORMAL");
        return Result.success();
    }
}
