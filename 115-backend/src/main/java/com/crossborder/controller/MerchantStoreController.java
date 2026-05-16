package com.crossborder.controller;

import com.crossborder.common.Result;
import com.crossborder.entity.MerchantStore;
import com.crossborder.service.AuthService;
import com.crossborder.service.MerchantStoreService;
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
@RequestMapping("/api/merchant")
@RequiredArgsConstructor
public class MerchantStoreController {
    private final AuthService authService;
    private final MerchantStoreService service;

    @GetMapping("/page")
    public Result<IPage<MerchantStore>> page(@RequestAttribute String role,
                                             @RequestParam(required = false) Integer pageNum,
                                             @RequestParam(required = false) Integer pageSize,
                                             @RequestParam(required = false) String keyword,
                                             @RequestParam(required = false) String status) {
        authService.assertAdminOrOperator(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody MerchantStore entity) {
        authService.assertAdminOrOperator(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody MerchantStore entity) {
        authService.assertAdminOrOperator(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/approve/{id}")
    public Result<Void> approve(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.updateStatus(id, "ACTIVE");
        return Result.success();
    }

    @PutMapping("/suspend/{id}")
    public Result<Void> suspend(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.updateStatus(id, "SUSPENDED");
        return Result.success();
    }
}
