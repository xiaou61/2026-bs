package com.movie.controller;

import com.movie.common.Result;
import com.movie.entity.TicketOrder;
import com.movie.service.TicketOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/order")
public class TicketOrderController {

    @Resource
    private TicketOrderService ticketOrderService;

    @PostMapping
    public Result<?> create(@RequestBody TicketOrder order, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        order.setUserId(userId);
        ticketOrderService.createOrder(order);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String orderNo,
                             @RequestParam(required = false) Integer status) {
        return Result.success(ticketOrderService.getPage(pageNum, pageSize, orderNo, status));
    }

    @GetMapping("/my")
    public Result<?> myOrders(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(ticketOrderService.getMyOrders(userId));
    }

    @PutMapping("/pay/{id}")
    public Result<?> pay(@PathVariable Long id) {
        ticketOrderService.pay(id);
        return Result.success();
    }

    @PutMapping("/cancel/{id}")
    public Result<?> cancel(@PathVariable Long id) {
        ticketOrderService.cancel(id);
        return Result.success();
    }

    @PutMapping("/complete/{id}")
    public Result<?> complete(@PathVariable Long id) {
        ticketOrderService.complete(id);
        return Result.success();
    }
}
