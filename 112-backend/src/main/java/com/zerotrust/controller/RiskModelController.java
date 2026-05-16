package com.zerotrust.controller;

import com.zerotrust.common.Result;
import com.zerotrust.entity.RiskModel;
import com.zerotrust.service.AuthService;
import com.zerotrust.service.RiskModelService;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/api/risk-model")
@RequiredArgsConstructor
public class RiskModelController {
    private final AuthService authService;
    private final RiskModelService service;

    @GetMapping("/page")
    public Result<PageInfo<RiskModel>> page(@RequestAttribute String role,
                                        @RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        authService.assertAdminOrSecurityOrAuditor(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody RiskModel entity) {
        authService.assertAdminOrSecurity(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody RiskModel entity) {
        authService.assertAdminOrSecurity(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/publish/{id}")
    public Result<Void> publish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrSecurity(role);
        service.updateStatus(id, "ACTIVE");
        return Result.success();
    }

    @PutMapping("/archive/{id}")
    public Result<Void> archive(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrAuditor(role);
        service.updateStatus(id, "ARCHIVED");
        return Result.success();
    }

}
