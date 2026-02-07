package com.milk.controller;

import com.milk.common.Result;
import com.milk.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Resource
    private NotificationService notificationService;

    @GetMapping("/my")
    public Result<?> my(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(notificationService.listByUserId(userId));
    }

    @PutMapping("/read/{id}")
    public Result<?> read(@PathVariable Long id) {
        notificationService.read(id);
        return Result.success();
    }

    @PutMapping("/readAll")
    public Result<?> readAll(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        notificationService.readAll(userId);
        return Result.success();
    }
}
