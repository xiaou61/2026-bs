package com.ticket.controller;

import com.ticket.common.Result;
import com.ticket.entity.Coupon;
import com.ticket.service.CouponService;
import com.ticket.utils.AuthUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Resource
    private CouponService couponService;

    @GetMapping("/public/list")
    public Result<?> publicList() {
        return Result.success(couponService.publicList());
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(couponService.page(pageNum, pageSize, name, status));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Coupon coupon, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        couponService.save(coupon);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        couponService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/receive/{id}")
    public Result<?> receive(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        couponService.receive(userId, id);
        return Result.success();
    }

    @GetMapping("/my")
    public Result<?> my(@RequestParam(required = false) String status, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(couponService.myCoupons(userId, status));
    }

    @GetMapping("/available")
    public Result<?> available(@RequestParam(required = false) BigDecimal amount, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(couponService.availableForOrder(userId, amount));
    }
}
