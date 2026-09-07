package com.privacyauth.controller;

import com.privacyauth.common.Result;
import com.privacyauth.entity.AuthorizationRecord;
import com.privacyauth.service.AuthService;
import com.privacyauth.service.AuthorizationRecordService;
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
@RequestMapping("/api/authorization")
@RequiredArgsConstructor
public class AuthorizationRecordController {
    private final AuthService authService;
    private final AuthorizationRecordService service;

    @GetMapping("/page")
    public Result<PageInfo<AuthorizationRecord>> page(@RequestAttribute String role,
                                        @RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        authService.assertAdminOrPrivacyOrAuditor(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody AuthorizationRecord entity) {
        authService.assertAdminOrPrivacy(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody AuthorizationRecord entity) {
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

    @PutMapping("/activate/{id}")
    public Result<Void> activate(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrPrivacy(role);
        service.updateStatus(id, "ACTIVE");
        return Result.success();
    }

    @PutMapping("/expire/{id}")
    public Result<Void> expire(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrPrivacy(role);
        service.updateStatus(id, "EXPIRED");
        return Result.success();
    }

    @PutMapping("/revoke/{id}")
    public Result<Void> revoke(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrAuditor(role);
        service.updateStatus(id, "REVOKED");
        return Result.success();
    }

}
