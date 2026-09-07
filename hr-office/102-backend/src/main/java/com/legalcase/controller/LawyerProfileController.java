package com.legalcase.controller;

import com.legalcase.common.Result;
import com.legalcase.entity.LawyerProfile;
import com.legalcase.service.AuthService;
import com.legalcase.service.OperationLogService;
import com.legalcase.service.LawyerProfileService;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/api/lawyer")
public class LawyerProfileController {
    @Autowired
    private LawyerProfileService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<PageInfo<LawyerProfile>> page(@RequestAttribute String role,
                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          String keyword,
                                          Integer serviceStatus) {
        authService.assertAdmin(role);
        return Result.success(service.page(pageNum, pageSize, keyword, serviceStatus));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody LawyerProfile lawyerProfile) {
        authService.assertAdmin(role);
        service.saveEntity(lawyerProfile);
        operationLogService.record(userId, "律师档案", "新增", "新增律师档案");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody LawyerProfile lawyerProfile) {
        authService.assertAdmin(role);
        service.saveEntity(lawyerProfile);
        operationLogService.record(userId, "律师档案", "编辑", "编辑律师档案：" + lawyerProfile.getId());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        operationLogService.record(userId, "律师档案", "删除", "删除律师档案：" + id);
        return Result.success();
    }
}
