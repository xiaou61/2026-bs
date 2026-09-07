package com.aigccopyright.controller;

import com.aigccopyright.common.Result;
import com.aigccopyright.entity.OperationLog;
import com.aigccopyright.service.AuthService;
import com.aigccopyright.service.OperationLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/log")
public class LogController {

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private AuthService authService;

    @GetMapping("/page")
    public Result<Page<OperationLog>> page(@RequestAttribute String role,
                                           @RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           String keyword,
                                           String moduleName) {
        authService.assertAdmin(role);
        LambdaQueryWrapper<OperationLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(OperationLog::getUsername, keyword).or().like(OperationLog::getDescription, keyword));
        wrapper.eq(StringUtils.hasText(moduleName), OperationLog::getModuleName, moduleName);
        wrapper.orderByDesc(OperationLog::getId);
        return Result.success(operationLogService.page(new Page<>(pageNum, pageSize), wrapper));
    }
}
