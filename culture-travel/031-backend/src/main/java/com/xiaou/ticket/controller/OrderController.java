package com.xiaou.ticket.controller;

import com.xiaou.ticket.dto.OrderCreateRequest;
import com.xiaou.ticket.dto.Result;
import com.xiaou.ticket.entity.Ticket;
import com.xiaou.ticket.entity.TicketOrder;
import com.xiaou.ticket.service.OrderService;
import com.xiaou.ticket.util.AuthContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Result<TicketOrder> createOrder(@RequestBody OrderCreateRequest request) {
        try {
            TicketOrder order = orderService.createOrder(AuthContext.getUserId(), request);
            return Result.success(order);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/{id}/pay")
    public Result<TicketOrder> payOrder(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            TicketOrder order = orderService.getOrderById(id);
            if (!canAccessUser(order.getUserId())) {
                return Result.error(403, "无权支付该订单");
            }
            String paymentMethod = request == null ? null : request.get("paymentMethod");
            return Result.success(orderService.payOrder(id, paymentMethod));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public Result<List<TicketOrder>> getUserOrders(@PathVariable Long userId) {
        if (!canAccessUser(userId)) {
            return Result.error(403, "无权查看其他用户订单");
        }
        return Result.success(orderService.getUserOrders(userId));
    }

    @GetMapping("/{id}")
    public Result<TicketOrder> getOrderById(@PathVariable Long id) {
        try {
            TicketOrder order = orderService.getOrderById(id);
            if (!canAccessUser(order.getUserId())) {
                return Result.error(403, "无权查看该订单");
            }
            return Result.success(order);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}/tickets")
    public Result<List<Ticket>> getOrderTickets(@PathVariable Long id) {
        try {
            TicketOrder order = orderService.getOrderById(id);
            if (!canAccessUser(order.getUserId())) {
                return Result.error(403, "无权查看该订单门票");
            }
            return Result.success(orderService.getOrderTickets(id));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> cancelOrder(@PathVariable Long id) {
        try {
            TicketOrder order = orderService.getOrderById(id);
            if (!canAccessUser(order.getUserId())) {
                return Result.error(403, "无权取消该订单");
            }
            orderService.cancelOrder(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    private boolean canAccessUser(Long userId) {
        return AuthContext.isAdmin() || userId.equals(AuthContext.getUserId());
    }
}
