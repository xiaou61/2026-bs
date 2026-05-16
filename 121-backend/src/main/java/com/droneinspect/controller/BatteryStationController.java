package com.droneinspect.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.droneinspect.common.Result;
import com.droneinspect.entity.BatteryStation;
import com.droneinspect.service.AuthService;
import com.droneinspect.service.BatteryStationService;
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
@RequestMapping("/api/station")
@RequiredArgsConstructor
public class BatteryStationController {
    private final AuthService authService;
    private final BatteryStationService service;

    @GetMapping("/page")
    public Result<IPage<BatteryStation>> page(@RequestAttribute String role,
                                              @RequestParam(required = false) Integer pageNum,
                                              @RequestParam(required = false) Integer pageSize,
                                              @RequestParam(required = false) String keyword,
                                              @RequestParam(required = false) String status) {
        authService.assertAdminOrEngineerOrManager(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody BatteryStation entity) {
        authService.assertAdminOrEngineer(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody BatteryStation entity) {
        authService.assertAdminOrEngineer(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/enable/{id}")
    public Result<Void> enable(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrEngineer(role);
        service.updateStatus(id, "NORMAL");
        return Result.success();
    }

    @PutMapping("/warn/{id}")
    public Result<Void> warn(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrEngineer(role);
        service.updateStatus(id, "WARNING");
        return Result.success();
    }
}
