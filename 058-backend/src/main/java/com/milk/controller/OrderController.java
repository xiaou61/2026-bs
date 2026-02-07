package com.milk.controller;

import com.milk.common.Result;
import com.milk.service.MilkOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Resource
    private MilkOrderService milkOrderService;

    @GetMapping("/my")
    public Result<?> my(HttpServletRequest request,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(required = false) Integer status) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(milkOrderService.myOrders(userId, pageNum, pageSize, status));
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String orderNo,
                          @RequestParam(required = false) Integer status) {
        return Result.success(milkOrderService.page(pageNum, pageSize, orderNo, status));
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(milkOrderService.getById(id));
    }

    @PutMapping("/cancel/{id}")
    public Result<?> cancel(@PathVariable Long id) {
        milkOrderService.cancel(id);
        return Result.success();
    }
}
