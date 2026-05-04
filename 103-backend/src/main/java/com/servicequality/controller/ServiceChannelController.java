package com.servicequality.controller;

import com.servicequality.common.Result;
import com.servicequality.entity.ServiceChannel;
import com.servicequality.service.AuthService;
import com.servicequality.service.OperationLogService;
import com.servicequality.service.ServiceChannelService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/channel")
public class ServiceChannelController {
    @Autowired
    private ServiceChannelService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<ServiceChannel>> page(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, String keyword, String channelType, Integer status) {
        return Result.success(service.page(pageNum, pageSize, keyword, channelType, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody ServiceChannel entity) {
        authService.assertSupervisor(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "服务渠道", "新增", "新增服务渠道");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody ServiceChannel entity) {
        authService.assertSupervisor(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "服务渠道", "编辑", "编辑服务渠道：" + entity.getId());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertSupervisor(role);
        service.removeById(id);
        operationLogService.record(userId, "服务渠道", "删除", "删除服务渠道：" + id);
        return Result.success();
    }
}
