package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Notification;
import com.xiaou.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/list")
    public Result<List<Notification>> list(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<Notification> notifications = notificationService.listMyNotifications(userId);
        return Result.success(notifications);
    }

    @GetMapping("/unread-count")
    public Result<Long> getUnreadCount(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long count = notificationService.getUnreadCount(userId);
        return Result.success(count);
    }

    @PutMapping("/{id}/read")
    public Result<String> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return Result.success("已标记为已读");
    }

    @PutMapping("/read-all")
    public Result<String> markAllAsRead(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        notificationService.markAllAsRead(userId);
        return Result.success("全部已读");
    }
}

