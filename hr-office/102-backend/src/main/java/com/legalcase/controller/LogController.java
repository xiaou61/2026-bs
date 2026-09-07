package com.legalcase.controller;

import com.legalcase.common.Result;
import com.legalcase.entity.OperationLog;
import com.legalcase.service.AuthService;
import com.legalcase.service.OperationLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/log")
public class LogController {
    @Autowired
    private OperationLogService service;

    @Autowired
    private AuthService authService;

    @GetMapping("/page")
    public Result<PageInfo<OperationLog>> page(@RequestAttribute String role,
                                               @RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               String keyword,
                                               String moduleName,
                                               String roleName) {
        authService.assertAdmin(role);
        return Result.success(service.page(pageNum, pageSize, keyword, moduleName, roleName));
    }
}
