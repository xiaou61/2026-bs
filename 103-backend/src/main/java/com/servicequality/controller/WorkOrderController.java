package com.servicequality.controller;

import com.servicequality.common.Result;
import com.servicequality.entity.WorkOrder;
import com.servicequality.service.AuthService;
import com.servicequality.service.OperationLogService;
import com.servicequality.service.WorkOrderService;
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
@RequestMapping("/api/order")
public class WorkOrderController {
    @Autowired
    private WorkOrderService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<WorkOrder>> page(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, String keyword, Long customerId, Long agentId, Integer status, String priority) {
        return Result.success(service.page(pageNum, pageSize, keyword, customerId, agentId, status, priority));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody WorkOrder entity) {
        authService.assertStaff(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "客服工单", "新增", "新增客服工单");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody WorkOrder entity) {
        authService.assertStaff(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "客服工单", "编辑", "编辑客服工单：" + entity.getId());
        return Result.success();
    }

    @PutMapping("/accept/{id}")
    public Result<Void> accept(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.accept(id, userId);
        operationLogService.record(userId, "客服工单", "受理", "受理工单：" + id);
        return Result.success();
    }

    @PutMapping("/resolve/{id}")
    public Result<Void> resolve(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.resolve(id);
        operationLogService.record(userId, "客服工单", "解决", "解决工单：" + id);
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<Void> close(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.close(id);
        operationLogService.record(userId, "客服工单", "关闭", "关闭工单：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.removeById(id);
        operationLogService.record(userId, "客服工单", "删除", "删除客服工单：" + id);
        return Result.success();
    }
}
