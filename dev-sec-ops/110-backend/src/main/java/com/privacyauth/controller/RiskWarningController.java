package com.privacyauth.controller;

import com.privacyauth.common.Result;
import com.privacyauth.entity.RiskWarning;
import com.privacyauth.service.AuthService;
import com.privacyauth.service.RiskWarningService;
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
@RequestMapping("/api/risk-warning")
@RequiredArgsConstructor
public class RiskWarningController {
    private final AuthService authService;
    private final RiskWarningService service;

    @GetMapping("/page")
    public Result<PageInfo<RiskWarning>> page(@RequestAttribute String role,
                                        @RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        authService.assertAdminOrPrivacyOrAuditor(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody RiskWarning entity) {
        authService.assertAdminOrPrivacy(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody RiskWarning entity) {
        authService.assertAdminOrPrivacy(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/ack/{id}")
    public Result<Void> ack(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrPrivacy(role);
        service.updateStatus(id, "ACKED");
        return Result.success();
    }

    @PutMapping("/resolve/{id}")
    public Result<Void> resolve(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrPrivacy(role);
        service.updateStatus(id, "RESOLVED");
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<Void> close(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrAuditor(role);
        service.updateStatus(id, "CLOSED");
        return Result.success();
    }

}
