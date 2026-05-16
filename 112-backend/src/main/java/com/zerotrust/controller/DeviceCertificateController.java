package com.zerotrust.controller;

import com.zerotrust.common.Result;
import com.zerotrust.entity.DeviceCertificate;
import com.zerotrust.service.AuthService;
import com.zerotrust.service.DeviceCertificateService;
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
@RequestMapping("/api/certificate")
@RequiredArgsConstructor
public class DeviceCertificateController {
    private final AuthService authService;
    private final DeviceCertificateService service;

    @GetMapping("/page")
    public Result<PageInfo<DeviceCertificate>> page(@RequestAttribute String role,
                                        @RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        authService.assertAdminOrNetwork(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody DeviceCertificate entity) {
        authService.assertAdminOrNetwork(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody DeviceCertificate entity) {
        authService.assertAdminOrNetwork(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/revoke/{id}")
    public Result<Void> revoke(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrNetwork(role);
        service.updateStatus(id, "REVOKED");
        return Result.success();
    }

    @PutMapping("/renew/{id}")
    public Result<Void> renew(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrNetwork(role);
        service.updateStatus(id, "VALID");
        return Result.success();
    }

}
