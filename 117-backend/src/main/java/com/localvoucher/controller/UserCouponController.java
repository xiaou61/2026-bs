package com.localvoucher.controller;

import com.localvoucher.common.Result;
import com.localvoucher.entity.UserCoupon;
import com.localvoucher.service.UserCouponService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
public class UserCouponController {
    private final UserCouponService service;

    @GetMapping("/page")
    public Result<IPage<UserCoupon>> page(@RequestParam(required = false) Integer pageNum,
                                     @RequestParam(required = false) Integer pageSize,
                                     @RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody UserCoupon entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody UserCoupon entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/lock/{id}")
    public Result<Void> lock(@PathVariable Long id) {
        service.updateStatus(id, "LOCKED");
        return Result.success();
    }

    @PutMapping("/use/{id}")
    public Result<Void> use(@PathVariable Long id) {
        service.updateStatus(id, "USED");
        return Result.success();
    }

    @PutMapping("/expire/{id}")
    public Result<Void> expire(@PathVariable Long id) {
        service.updateStatus(id, "EXPIRED");
        return Result.success();
    }

}
