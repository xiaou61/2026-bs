package com.twinpark.controller;

import com.github.pagehelper.PageInfo;
import com.twinpark.common.Result;
import com.twinpark.entity.MaintenanceSchedule;
import com.twinpark.service.AuthService;
import com.twinpark.service.MaintenanceScheduleService;
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
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class MaintenanceScheduleController {
    private final AuthService authService;
    private final MaintenanceScheduleService service;

    @GetMapping("/page")
    public Result<PageInfo<MaintenanceSchedule>> page(@RequestAttribute String role,
                                                      @RequestParam(required = false) Integer pageNum,
                                                      @RequestParam(required = false) Integer pageSize,
                                                      @RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false) String status) {
        authService.assertAdminOrEngineerOrManager(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody MaintenanceSchedule entity) {
        authService.assertAdminOrEngineer(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody MaintenanceSchedule entity) {
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

    @PutMapping("/start/{id}")
    public Result<Void> start(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrEngineer(role);
        service.updateStatus(id, "EXECUTING");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrEngineer(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }
}
