package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.dto.OrderDTO;
import com.xiaou.entity.RecycleOrder;
import com.xiaou.service.RecycleOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "回收订单")
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final RecycleOrderService orderService;

    @Operation(summary = "创建预约订单")
    @PostMapping
    public Result<Void> create(@AuthenticationPrincipal Long userId,
                               @RequestBody OrderDTO dto) {
        orderService.createOrder(dto, userId);
        return Result.success();
    }

    @Operation(summary = "我的订单列表(居民)")
    @GetMapping("/my")
    public Result<IPage<RecycleOrder>> myOrders(@AuthenticationPrincipal Long userId,
                                                @RequestParam(defaultValue = "1") Integer current,
                                                @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(orderService.pageByUser(userId, current, size));
    }

    @Operation(summary = "订单详情")
    @GetMapping("/{id}")
    public Result<RecycleOrder> detail(@PathVariable Long id) {
        return Result.success(orderService.getById(id));
    }

    @Operation(summary = "取消订单")
    @PostMapping("/{id}/cancel")
    public Result<Void> cancel(@PathVariable Long id,
                               @RequestParam(required = false) String reason) {
        orderService.cancelOrder(id, reason);
        return Result.success();
    }
}
