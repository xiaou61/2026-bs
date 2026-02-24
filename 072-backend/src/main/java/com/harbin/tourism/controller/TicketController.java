package com.harbin.tourism.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.common.Result;
import com.harbin.tourism.entity.TicketOrder;
import com.harbin.tourism.entity.TicketType;
import com.harbin.tourism.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/types/{spotId}")
    public Result<List<TicketType>> getTypes(@PathVariable Long spotId) {
        return Result.success(ticketService.getTicketTypes(spotId));
    }

    @PostMapping("/types")
    public Result<Void> addType(@RequestBody TicketType ticketType) {
        ticketService.saveTicketType(ticketType);
        return Result.success();
    }

    @PutMapping("/types")
    public Result<Void> updateType(@RequestBody TicketType ticketType) {
        ticketService.updateTicketType(ticketType);
        return Result.success();
    }

    @DeleteMapping("/types/{id}")
    public Result<Void> deleteType(@PathVariable Long id) {
        ticketService.deleteTicketType(id);
        return Result.success();
    }

    @PostMapping("/order")
    public Result<TicketOrder> createOrder(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long spotId = Long.valueOf(params.get("spotId").toString());
        Long ticketTypeId = Long.valueOf(params.get("ticketTypeId").toString());
        String ticketDate = (String) params.get("ticketDate");
        Integer quantity = (Integer) params.get("quantity");
        return Result.success(ticketService.createOrder(userId, spotId, ticketTypeId, ticketDate, quantity));
    }

    @GetMapping("/orders/my")
    public Result<Page<TicketOrder>> myOrders(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String status,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(ticketService.userOrders(userId, pageNum, pageSize, status));
    }

    @GetMapping("/orders")
    public Result<Page<TicketOrder>> allOrders(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String orderNo) {
        return Result.success(ticketService.allOrders(pageNum, pageSize, status, orderNo));
    }

    @GetMapping("/orders/{id}")
    public Result<TicketOrder> getOrder(@PathVariable Long id) {
        return Result.success(ticketService.getOrderById(id));
    }

    @PostMapping("/orders/{id}/refund")
    public Result<Void> refund(@PathVariable Long id) {
        ticketService.refund(id);
        return Result.success();
    }

    @PutMapping("/orders/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> params) {
        ticketService.updateOrderStatus(id, params.get("status"));
        return Result.success();
    }
}
