package com.servicequality.controller;

import com.servicequality.common.Result;
import com.servicequality.entity.CustomerProfile;
import com.servicequality.service.AuthService;
import com.servicequality.service.OperationLogService;
import com.servicequality.service.CustomerProfileService;
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
@RequestMapping("/api/customer")
public class CustomerProfileController {
    @Autowired
    private CustomerProfileService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<CustomerProfile>> page(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, String keyword, String levelName, Integer status) {
        return Result.success(service.page(pageNum, pageSize, keyword, levelName, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody CustomerProfile entity) {
        authService.assertStaff(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "客户档案", "新增", "新增客户档案");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody CustomerProfile entity) {
        authService.assertStaff(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "客户档案", "编辑", "编辑客户档案：" + entity.getId());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.removeById(id);
        operationLogService.record(userId, "客户档案", "删除", "删除客户档案：" + id);
        return Result.success();
    }
}
