package com.privacyauth.controller;

import com.privacyauth.common.Result;
import com.privacyauth.entity.AccessGrant;
import com.privacyauth.service.AuthService;
import com.privacyauth.service.AccessGrantService;
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
@RequestMapping("/api/grant")
@RequiredArgsConstructor
public class AccessGrantController {
    private final AuthService authService;
    private final AccessGrantService service;

    @GetMapping("/page")
    public Result<PageInfo<AccessGrant>> page(@RequestAttribute String role,
                                        @RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody AccessGrant entity) {
        authService.assertAdminOrPrivacy(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody AccessGrant entity) {
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

    @PutMapping("/revoke/{id}")
    public Result<Void> revoke(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrPrivacy(role);
        service.updateStatus(id, "REVOKED");
        return Result.success();
    }

}
