package com.localvoucher.controller;

import com.localvoucher.common.Result;
import com.localvoucher.entity.StoreBranch;
import com.localvoucher.service.AuthService;
import com.localvoucher.service.StoreBranchService;
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
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreBranchController {
    private final AuthService authService;
    private final StoreBranchService service;

    @GetMapping("/page")
    public Result<IPage<StoreBranch>> page(@RequestAttribute String role,
                                           @RequestParam(required = false) Integer pageNum,
                                           @RequestParam(required = false) Integer pageSize,
                                           @RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) String status) {
        authService.assertAdminOrMerchant(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody StoreBranch entity) {
        authService.assertAdminOrMerchant(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody StoreBranch entity) {
        authService.assertAdminOrMerchant(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/open/{id}")
    public Result<Void> open(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrMerchant(role);
        service.updateStatus(id, "ACTIVE");
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<Void> close(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrMerchant(role);
        service.updateStatus(id, "CLOSED");
        return Result.success();
    }
}
