package com.xiaou.artist.controller;

import com.xiaou.artist.common.Result;
import com.xiaou.artist.entity.Order;
import com.xiaou.artist.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @PostMapping("/create")
    public Result<Order> createOrder(@RequestBody Order order) {
        Order newOrder = orderService.createOrder(order);
        return Result.success(newOrder);
    }
    
    @GetMapping("/user/{userId}")
    public Result<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return Result.success(orders);
    }
    
    @GetMapping("/artist/{artistId}")
    public Result<List<Order>> getOrdersByArtistId(@PathVariable Long artistId) {
        List<Order> orders = orderService.getOrdersByArtistId(artistId);
        return Result.success(orders);
    }
    
    @GetMapping("/list")
    public Result<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return Result.success(orders);
    }
    
    @GetMapping("/{id}")
    public Result<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return Result.success(order);
    }
    
    @PostMapping("/pay-deposit/{id}")
    public Result<String> payDeposit(@PathVariable Long id) {
        boolean success = orderService.payDeposit(id);
        return success ? Result.success("定金支付成功") : Result.error("支付失败");
    }
    
    @PostMapping("/submit-draft")
    public Result<String> submitDraft(@RequestParam Long id, @RequestParam String url) {
        boolean success = orderService.submitDraft(id, url);
        return success ? Result.success("草图已提交") : Result.error("提交失败");
    }
    
    @PostMapping("/confirm-draft/{id}")
    public Result<String> confirmDraft(@PathVariable Long id) {
        boolean success = orderService.confirmDraft(id);
        return success ? Result.success("草图已确认") : Result.error("确认失败");
    }
    
    @PostMapping("/request-revise/{id}")
    public Result<String> requestRevise(@PathVariable Long id) {
        boolean success = orderService.requestRevise(id);
        return success ? Result.success("已提交修改请求") : Result.error("操作失败");
    }
    
    @PostMapping("/submit-final")
    public Result<String> submitFinal(@RequestParam Long id, @RequestParam String url) {
        boolean success = orderService.submitFinal(id, url);
        return success ? Result.success("成品已提交") : Result.error("提交失败");
    }
    
    @PostMapping("/pay-final/{id}")
    public Result<String> payFinalPayment(@PathVariable Long id) {
        boolean success = orderService.payFinalPayment(id);
        return success ? Result.success("尾款支付成功") : Result.error("支付失败");
    }
    
    @PostMapping("/complete/{id}")
    public Result<String> completeOrder(@PathVariable Long id) {
        boolean success = orderService.completeOrder(id);
        return success ? Result.success("订单已完成") : Result.error("操作失败");
    }
}
