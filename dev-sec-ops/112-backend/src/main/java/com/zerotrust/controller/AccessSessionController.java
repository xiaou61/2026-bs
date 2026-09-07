package com.zerotrust.controller;

import com.zerotrust.common.Result;
import com.zerotrust.entity.AccessSession;
import com.zerotrust.service.AuthService;
import com.zerotrust.service.AccessSessionService;
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
@RequestMapping("/api/session")
@RequiredArgsConstructor
public class AccessSessionController {
    private final AuthService authService;
    private final AccessSessionService service;

    @GetMapping("/page")
    public Result<PageInfo<AccessSession>> page(@RequestAttribute String role,
                                        @RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        authService.assertAdminOrNetworkOrAuditor(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody AccessSession entity) {
        authService.assertAdminOrNetwork(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody AccessSession entity) {
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

    @PutMapping("/terminate/{id}")
    public Result<Void> terminate(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrNetwork(role);
        service.updateStatus(id, "TERMINATED");
        return Result.success();
    }

    @PutMapping("/expire/{id}")
    public Result<Void> expire(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrNetwork(role);
        service.updateStatus(id, "EXPIRED");
        return Result.success();
    }

}
