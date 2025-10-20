package com.xiaou.express.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.express.common.Result;
import com.xiaou.express.dto.OrderCreateRequest;
import com.xiaou.express.service.OrderService;
import com.xiaou.express.util.UserContext;
import com.xiaou.express.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Result<Void> createOrder(@RequestBody OrderCreateRequest request) {
        Long userId = UserContext.getCurrentUserId();
        orderService.createOrder(userId, request);
        return Result.success();
    }

    @GetMapping
    public Result<Page<OrderVO>> getOrderSquare(@RequestParam(defaultValue = "1") int pageNum,
                                                  @RequestParam(defaultValue = "10") int pageSize) {
        Page<OrderVO> page = orderService.getOrderSquare(pageNum, pageSize);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result<OrderVO> getOrderDetail(@PathVariable Long id) {
        OrderVO order = orderService.getOrderDetail(id);
        return Result.success(order);
    }

    @PostMapping("/{id}/accept")
    public Result<Void> acceptOrder(@PathVariable Long id) {
        Long userId = UserContext.getCurrentUserId();
        orderService.acceptOrder(userId, id);
        return Result.success();
    }

    @PostMapping("/{id}/pickup")
    public Result<Void> uploadPickupImage(@PathVariable Long id, @RequestBody String image) {
        Long userId = UserContext.getCurrentUserId();
        orderService.uploadPickupImage(userId, id, image);
        return Result.success();
    }

    @PostMapping("/{id}/deliver")
    public Result<Void> uploadDeliveryImage(@PathVariable Long id, @RequestBody String image) {
        Long userId = UserContext.getCurrentUserId();
        orderService.uploadDeliveryImage(userId, id, image);
        return Result.success();
    }

    @PostMapping("/{id}/confirm")
    public Result<Void> confirmOrder(@PathVariable Long id) {
        Long userId = UserContext.getCurrentUserId();
        orderService.confirmOrder(userId, id);
        return Result.success();
    }

    @PostMapping("/{id}/cancel")
    public Result<Void> cancelOrder(@PathVariable Long id, @RequestBody String reason) {
        Long userId = UserContext.getCurrentUserId();
        orderService.cancelOrder(userId, id, reason);
        return Result.success();
    }

    @GetMapping("/my/published")
    public Result<Page<OrderVO>> getMyPublishedOrders(@RequestParam(defaultValue = "1") int pageNum,
                                                        @RequestParam(defaultValue = "10") int pageSize,
                                                        @RequestParam(required = false) Integer status) {
        Long userId = UserContext.getCurrentUserId();
        Page<OrderVO> page = orderService.getMyPublishedOrders(userId, pageNum, pageSize, status);
        return Result.success(page);
    }

    @GetMapping("/my/accepted")
    public Result<Page<OrderVO>> getMyAcceptedOrders(@RequestParam(defaultValue = "1") int pageNum,
                                                       @RequestParam(defaultValue = "10") int pageSize,
                                                       @RequestParam(required = false) Integer status) {
        Long userId = UserContext.getCurrentUserId();
        Page<OrderVO> page = orderService.getMyAcceptedOrders(userId, pageNum, pageSize, status);
        return Result.success(page);
    }
}

