package com.agritrace.controller;

import com.agritrace.common.Result;
import com.agritrace.entity.LogisticsRecord;
import com.agritrace.service.AuthService;
import com.agritrace.service.LogisticsRecordService;
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
@RequestMapping("/api/logistics")
@RequiredArgsConstructor
public class LogisticsRecordController {
    private final AuthService authService;
    private final LogisticsRecordService service;

    @GetMapping("/page")
    public Result<IPage<LogisticsRecord>> page(@RequestAttribute String role,
                                               @RequestParam(required = false) Integer pageNum,
                                               @RequestParam(required = false) Integer pageSize,
                                               @RequestParam(required = false) String keyword,
                                               @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody LogisticsRecord entity) {
        authService.assertAdminOrRegulator(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody LogisticsRecord entity) {
        authService.assertAdminOrRegulator(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/depart/{id}")
    public Result<Void> depart(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrRegulator(role);
        service.updateStatus(id, "IN_TRANSIT");
        return Result.success();
    }

    @PutMapping("/arrive/{id}")
    public Result<Void> arrive(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrRegulator(role);
        service.updateStatus(id, "ARRIVED");
        return Result.success();
    }

    @PutMapping("/sign/{id}")
    public Result<Void> sign(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrRegulator(role);
        service.updateStatus(id, "SIGNED");
        return Result.success();
    }
}
