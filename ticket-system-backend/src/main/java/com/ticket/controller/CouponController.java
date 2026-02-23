package com.ticket.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.Result;
import com.ticket.entity.Coupon;
import com.ticket.entity.UserCoupon;
import com.ticket.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/list")
    public Result<Page<Coupon>> listCoupons(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status) {
        return Result.success(couponService.listCoupons(pageNum, pageSize, status));
    }

    @GetMapping("/available")
    public Result<List<Coupon>> getAvailableCoupons() {
        return Result.success(couponService.getAvailableCoupons());
    }

    @PostMapping("/receive/{id}")
    public Result<Void> receiveCoupon(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        couponService.receiveCoupon(id, userId);
        return Result.success();
    }

    @GetMapping("/my")
    public Result<List<UserCoupon>> getMyCoupons(
            @RequestParam(required = false) String status,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(couponService.getMyCoupons(userId, status));
    }

    @PostMapping("/add")
    public Result<Void> addCoupon(@RequestBody Coupon coupon) {
        couponService.addCoupon(coupon);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<Void> updateCoupon(@RequestBody Coupon coupon) {
        couponService.updateCoupon(coupon);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return Result.success();
    }
}
