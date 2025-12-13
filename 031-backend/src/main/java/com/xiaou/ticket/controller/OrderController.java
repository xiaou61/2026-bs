package com.xiaou.ticket.controller;

import com.xiaou.ticket.dto.Result;
import com.xiaou.ticket.entity.TicketOrder;
import com.xiaou.ticket.service.OrderService;
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
    public Result<TicketOrder> createOrder(@RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            Long matchId = Long.valueOf(request.get("matchId").toString());
            @SuppressWarnings("unchecked")
            List<Long> seatIds = (List<Long>) request.get("seatIds");
            
            TicketOrder order = orderService.createOrder(userId, matchId, seatIds);
            return Result.success(order);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PostMapping("/{id}/pay")
    public Result<TicketOrder> payOrder(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            String paymentMethod = request.get("paymentMethod");
            TicketOrder order = orderService.payOrder(id, paymentMethod);
            return Result.success(order);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/user/{userId}")
    public Result<List<TicketOrder>> getUserOrders(@PathVariable Long userId) {
        return Result.success(orderService.getUserOrders(userId));
    }
    
    @GetMapping("/{id}")
    public Result<TicketOrder> getOrderById(@PathVariable Long id) {
        try {
            return Result.success(orderService.getOrderById(id));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<Void> cancelOrder(@PathVariable Long id) {
        try {
            orderService.cancelOrder(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
