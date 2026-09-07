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

import java.util.List;

@Tag(name = "回收员接口")
@RestController
@RequestMapping("/api/collector")
@RequiredArgsConstructor
public class CollectorController {

    private final RecycleOrderService orderService;

    @Operation(summary = "订单列表(回收员)")
    @GetMapping("/orders")
    public Result<IPage<RecycleOrder>> orders(@AuthenticationPrincipal Long userId,
                                              @RequestParam(defaultValue = "1") Integer current,
                                              @RequestParam(defaultValue = "10") Integer size,
                                              @RequestParam(required = false) Integer status) {
        return Result.success(orderService.pageByCollector(userId, current, size, status));
    }

    @Operation(summary = "接单")
    @PostMapping("/accept/{orderId}")
    public Result<Void> accept(@AuthenticationPrincipal Long userId,
                               @PathVariable Long orderId) {
        orderService.acceptOrder(orderId, userId);
        return Result.success();
    }

    @Operation(summary = "完成订单")
    @PostMapping("/complete/{orderId}")
    public Result<Void> complete(@PathVariable Long orderId,
                                 @RequestBody List<OrderDTO.OrderDetailDTO> details) {
        orderService.completeOrder(orderId, details);
        return Result.success();
    }
}
