package com.datamasking.controller;

import com.datamasking.common.Result;
import com.datamasking.entity.AccessRequest;
import com.datamasking.service.AuthService;
import com.datamasking.service.AccessRequestService;
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
@RequestMapping("/api/access-request")
@RequiredArgsConstructor
public class AccessRequestController {
    private final AuthService authService;
    private final AccessRequestService service;

    @GetMapping("/page")
    public Result<IPage<AccessRequest>> page(@RequestAttribute String role,
                                            @RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody AccessRequest entity) {
        authService.assertAuthenticated(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody AccessRequest entity) {
        authService.assertAuthenticated(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/submit/{id}")
    public Result<Void> submit(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAuthenticated(role);
        service.updateStatus(id, "SUBMITTED");
        return Result.success();
    }

    @PutMapping("/approve/{id}")
    public Result<Void> approve(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrOwner(role);
        service.updateStatus(id, "APPROVED");
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<Void> reject(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrOwner(role);
        service.updateStatus(id, "REJECTED");
        return Result.success();
    }

    @PutMapping("/revoke/{id}")
    public Result<Void> revoke(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAuthenticated(role);
        service.updateStatus(id, "REVOKED");
        return Result.success();
    }

}
