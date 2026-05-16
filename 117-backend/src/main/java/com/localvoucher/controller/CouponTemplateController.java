package com.localvoucher.controller;

import com.localvoucher.common.Result;
import com.localvoucher.entity.CouponTemplate;
import com.localvoucher.service.AuthService;
import com.localvoucher.service.CouponTemplateService;
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
@RequestMapping("/api/template")
@RequiredArgsConstructor
public class CouponTemplateController {
    private final AuthService authService;
    private final CouponTemplateService service;

    @GetMapping("/page")
    public Result<IPage<CouponTemplate>> page(@RequestAttribute String role,
                                              @RequestParam(required = false) Integer pageNum,
                                              @RequestParam(required = false) Integer pageSize,
                                              @RequestParam(required = false) String keyword,
                                              @RequestParam(required = false) String status) {
        authService.assertAdmin(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody CouponTemplate entity) {
        authService.assertAdmin(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody CouponTemplate entity) {
        authService.assertAdmin(role);
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
        authService.assertAdmin(role);
        service.updateStatus(id, "PUBLISHED");
        return Result.success();
    }

    @PutMapping("/offline/{id}")
    public Result<Void> offline(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.updateStatus(id, "OFFLINE");
        return Result.success();
    }
}
