package com.phishingtraining.controller;

import com.phishingtraining.common.Result;
import com.phishingtraining.entity.RiskScore;
import com.phishingtraining.service.AuthService;
import com.phishingtraining.service.RiskScoreService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
@RequestMapping("/api/risk-score")
@RequiredArgsConstructor
public class RiskScoreController {
    private final AuthService authService;
    private final RiskScoreService service;

    @GetMapping("/page")
    public Result<IPage<RiskScore>> page(@RequestAttribute String role,
                                            @RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody RiskScore entity) {
        authService.assertAdminOrSecurity(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody RiskScore entity) {
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

    @PutMapping("/review/{id}")
    public Result<Void> review(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrAuditor(role);
        service.updateStatus(id, "REVIEWED");
        return Result.success();
    }

    @PutMapping("/remediate/{id}")
    public Result<Void> remediate(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrSecurity(role);
        service.updateStatus(id, "REMEDIATED");
        return Result.success();
    }

}
