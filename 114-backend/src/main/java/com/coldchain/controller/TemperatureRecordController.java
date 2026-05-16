package com.coldchain.controller;

import com.coldchain.common.Result;
import com.coldchain.entity.TemperatureRecord;
import com.coldchain.service.AuthService;
import com.coldchain.service.TemperatureRecordService;
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
@RequestMapping("/api/temperature")
@RequiredArgsConstructor
public class TemperatureRecordController {
    private final AuthService authService;
    private final TemperatureRecordService service;

    @GetMapping("/page")
    public Result<PageInfo<TemperatureRecord>> page(@RequestAttribute String role,
                                                    @RequestParam(required = false) Integer pageNum,
                                                    @RequestParam(required = false) Integer pageSize,
                                                    @RequestParam(required = false) String keyword,
                                                    @RequestParam(required = false) String status) {
        authService.assertAdminOrCarrierOrSupervisor(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody TemperatureRecord entity) {
        authService.assertAdminOrCarrier(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody TemperatureRecord entity) {
        authService.assertAdminOrCarrier(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/normal/{id}")
    public Result<Void> normal(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrCarrier(role);
        service.updateStatus(id, "NORMAL");
        return Result.success();
    }

    @PutMapping("/warn/{id}")
    public Result<Void> warn(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrCarrier(role);
        service.updateStatus(id, "WARNING");
        return Result.success();
    }

    @PutMapping("/alarm/{id}")
    public Result<Void> alarm(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrSupervisor(role);
        service.updateStatus(id, "ALARM");
        return Result.success();
    }
}
