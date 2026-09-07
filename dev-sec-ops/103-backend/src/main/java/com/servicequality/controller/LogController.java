package com.servicequality.controller;

import com.servicequality.common.Result;
import com.servicequality.entity.OperationLog;
import com.servicequality.service.OperationLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/log")
public class LogController {
    @Autowired
    private OperationLogService service;

    @GetMapping("/page")
    public Result<Page<OperationLog>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           String keyword,
                                           String moduleName,
                                           String role) {
        LambdaQueryWrapper<OperationLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(org.springframework.util.StringUtils.hasText(keyword), item -> item.like(OperationLog::getUsername, keyword).or().like(OperationLog::getModuleName, keyword).or().like(OperationLog::getDescription, keyword));
        wrapper.eq(org.springframework.util.StringUtils.hasText(moduleName), OperationLog::getModuleName, moduleName);
        wrapper.eq(org.springframework.util.StringUtils.hasText(role), OperationLog::getRole, role);
        wrapper.orderByDesc(OperationLog::getId);
        return Result.success(service.page(new Page<>(pageNum, pageSize), wrapper));
    }

}
