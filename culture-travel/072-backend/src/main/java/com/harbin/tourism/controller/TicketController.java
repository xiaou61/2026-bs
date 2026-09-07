package com.harbin.tourism.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.common.Result;
import com.harbin.tourism.entity.TicketOrder;
import com.harbin.tourism.entity.TicketType;
import com.harbin.tourism.service.TicketService;
import com.harbin.tourism.utils.AuthUtils;
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

    @GetMapping("/list")
    public Result<Page<Map<String, Object>>> ticketList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String spotName) {
        return Result.success(ticketService.ticketList(pageNum, pageSize, spotName));
    }

    @GetMapping("/types/{spotId}")
    public Result<List<TicketType>> getTypes(@PathVariable Long spotId) {
        return Result.success(ticketService.getTicketTypes(spotId));
    }

    @PostMapping("/types")
    public Result<Void> addType(@RequestBody TicketType ticketType, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        ticketService.saveTicketType(ticketType);
        return Result.success();
    }

    @PutMapping("/types")
    public Result<Void> updateType(@RequestBody TicketType ticketType, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        ticketService.updateTicketType(ticketType);
        return Result.success();
    }

    @DeleteMapping("/types/{id}")
    public Result<Void> deleteType(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        ticketService.deleteTicketType(id);
        return Result.success();
    }

    @PostMapping("/order")
    public Result<TicketOrder> createOrder(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Long userId = AuthUtils.currentUserId(request);
        Long spotId = params.get("spotId") == null ? null : Long.valueOf(params.get("spotId").toString());
        Long ticketTypeId = Long.valueOf(params.get("ticketTypeId").toString());
        String ticketDate = params.get("ticketDate") == null ? (String) params.get("visitDate") : (String) params.get("ticketDate");
        Integer quantity = (Integer) params.get("quantity");
        return Result.success(ticketService.createOrder(userId, spotId, ticketTypeId, ticketDate, quantity));
    }

    @GetMapping("/orders/my")
    public Result<Page<TicketOrder>> myOrders(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String status,
            HttpServletRequest request) {
        Long userId = AuthUtils.currentUserId(request);
        return Result.success(ticketService.userOrders(userId, pageNum, pageSize, status));
    }

    @GetMapping("/orders")
    public Result<Page<TicketOrder>> allOrders(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String orderNo,
            HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        return Result.success(ticketService.allOrders(pageNum, pageSize, status, orderNo));
    }

    @GetMapping("/orders/{id}")
    public Result<TicketOrder> getOrder(@PathVariable Long id, HttpServletRequest request) {
        return Result.success(ticketService.getOrderById(id, AuthUtils.currentUserId(request), AuthUtils.isAdmin(request)));
    }

    @PostMapping("/orders/{id}/refund")
    public Result<Void> refund(@PathVariable Long id, HttpServletRequest request) {
        ticketService.refund(id, AuthUtils.currentUserId(request), AuthUtils.isAdmin(request));
        return Result.success();
    }

    @PutMapping("/orders/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> params, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        ticketService.updateOrderStatus(id, params.get("status"));
        return Result.success();
    }
}
