package com.xiaou.campusshare.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.campusshare.common.Result;
import com.xiaou.campusshare.entity.OrderInfo;
import com.xiaou.campusshare.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public Result<List<OrderInfo>> getList(
            HttpServletRequest request,
            @RequestParam(required = false) Integer status) {
        Long userId = (Long) request.getAttribute("userId");
        List<OrderInfo> orders = orderService.getUserOrders(userId, status);
        return Result.success(orders);
    }

    @GetMapping("/{id}")
    public Result<OrderInfo> getDetail(@PathVariable Long id) {
        OrderInfo order = orderService.getById(id);
        return Result.success(order);
    }

    @PutMapping("/{id}/cancel")
    public Result<?> cancel(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        OrderInfo order = orderService.getById(id);
        if (order == null || !order.getUserId().equals(userId)) {
            return Result.error("无权限");
        }
        if (order.getOrderStatus() != 0) {
            return Result.error("订单状态不允许取消");
        }
        order.setOrderStatus(5);
        orderService.updateById(order);
        return Result.success("取消成功");
    }

    @GetMapping("/ongoing")
    public Result<List<OrderInfo>> getOngoing(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getUserId, userId);
        wrapper.in(OrderInfo::getOrderStatus, 1, 2);
        wrapper.orderByDesc(OrderInfo::getCreateTime);
        List<OrderInfo> orders = orderService.list(wrapper);
        return Result.success(orders);
    }

    @GetMapping("/history")
    public Result<List<OrderInfo>> getHistory(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getUserId, userId);
        wrapper.in(OrderInfo::getOrderStatus, 4, 5);
        wrapper.orderByDesc(OrderInfo::getCreateTime);
        List<OrderInfo> orders = orderService.list(wrapper);
        return Result.success(orders);
    }
}

