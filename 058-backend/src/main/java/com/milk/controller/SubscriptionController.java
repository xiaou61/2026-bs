package com.milk.controller;

import com.milk.common.Result;
import com.milk.entity.Subscription;
import com.milk.service.SubscriptionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Resource
    private SubscriptionService subscriptionService;

    @GetMapping("/my")
    public Result<?> my(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(subscriptionService.listByUserId(userId));
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long userId,
                          @RequestParam(required = false) String type) {
        return Result.success(subscriptionService.page(pageNum, pageSize, userId, type));
    }

    @PostMapping
    public Result<?> add(@RequestBody Subscription subscription, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        subscription.setUserId(userId);
        subscriptionService.save(subscription);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Subscription subscription) {
        subscriptionService.save(subscription);
        return Result.success();
    }

    @PutMapping("/pause/{id}")
    public Result<?> pause(@PathVariable Long id) {
        subscriptionService.pause(id);
        return Result.success();
    }

    @PutMapping("/resume/{id}")
    public Result<?> resume(@PathVariable Long id) {
        subscriptionService.resume(id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        subscriptionService.deleteById(id);
        return Result.success();
    }
}
