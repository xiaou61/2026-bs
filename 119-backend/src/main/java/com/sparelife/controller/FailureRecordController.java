package com.sparelife.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sparelife.common.Result;
import com.sparelife.entity.FailureRecord;
import com.sparelife.service.AuthService;
import com.sparelife.service.FailureRecordService;
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
@RequestMapping("/api/failure")
@RequiredArgsConstructor
public class FailureRecordController {
    private final AuthService authService;
    private final FailureRecordService service;

    @GetMapping("/page")
    public Result<IPage<FailureRecord>> page(@RequestAttribute String role,
                                             @RequestParam(required = false) Integer pageNum,
                                             @RequestParam(required = false) Integer pageSize,
                                             @RequestParam(required = false) String keyword,
                                             @RequestParam(required = false) String status) {
        authService.assertAdminOrDeviceAdminOrMaintainer(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody FailureRecord entity) {
        authService.assertAdminOrDeviceAdminOrMaintainer(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody FailureRecord entity) {
        authService.assertAdminOrDeviceAdminOrMaintainer(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/handle/{id}")
    public Result<Void> handle(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrMaintainer(role);
        service.updateStatus(id, "HANDLING");
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<Void> close(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrMaintainer(role);
        service.updateStatus(id, "CLOSED");
        return Result.success();
    }
}
