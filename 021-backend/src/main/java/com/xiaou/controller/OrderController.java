package com.xiaou.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.PageResult;
import com.xiaou.common.Result;
import com.xiaou.dto.OrderCreateDTO;
import com.xiaou.dto.OrderRateDTO;
import com.xiaou.service.OrderService;
import com.xiaou.vo.OrderDetailVO;
import com.xiaou.vo.OrderVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public Result<Long> createOrder(@RequestAttribute("userId") Long userId,
                                    @Valid @RequestBody OrderCreateDTO createDTO) {
        return Result.success(orderService.createOrder(userId, createDTO));
    }

    @GetMapping("/my/sell")
    public Result<PageResult<OrderVO>> getMySellOrders(@RequestAttribute("userId") Long userId,
                                                       @RequestParam(defaultValue = "1") Integer current,
                                                       @RequestParam(defaultValue = "10") Integer size) {
        Page<OrderVO> page = orderService.getMySellOrders(userId, current, size);
        return Result.success(PageResult.of(page.getTotal(), page.getRecords(), page.getCurrent(), page.getSize()));
    }

    @GetMapping("/my/buy")
    public Result<PageResult<OrderVO>> getMyBuyOrders(@RequestAttribute("userId") Long userId,
                                                      @RequestParam(defaultValue = "1") Integer current,
                                                      @RequestParam(defaultValue = "10") Integer size) {
        Page<OrderVO> page = orderService.getMyBuyOrders(userId, current, size);
        return Result.success(PageResult.of(page.getTotal(), page.getRecords(), page.getCurrent(), page.getSize()));
    }

    @GetMapping("/{id}")
    public Result<OrderDetailVO> getOrderDetail(@RequestAttribute("userId") Long userId,
                                                @PathVariable Long id) {
        return Result.success(orderService.getOrderDetail(userId, id));
    }

    @PutMapping("/{id}/complete")
    public Result<String> completeOrder(@RequestAttribute("userId") Long userId,
                                        @PathVariable Long id) {
        orderService.completeOrder(userId, id);
        return Result.success("确认收货成功");
    }

    @PutMapping("/{id}/cancel")
    public Result<String> cancelOrder(@RequestAttribute("userId") Long userId,
                                      @PathVariable Long id) {
        orderService.cancelOrder(userId, id);
        return Result.success("取消订单成功");
    }

    @PostMapping("/{id}/rate")
    public Result<String> rateOrder(@RequestAttribute("userId") Long userId,
                                    @PathVariable Long id,
                                    @Valid @RequestBody OrderRateDTO rateDTO) {
        orderService.rateOrder(userId, id, rateDTO);
        return Result.success("评价成功");
    }
}
