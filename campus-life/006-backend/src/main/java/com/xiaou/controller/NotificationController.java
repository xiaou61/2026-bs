package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Notification;
import com.xiaou.entity.User;
import com.xiaou.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/list")
    public Result<List<Notification>> getNotifications(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Notification> notifications = notificationService.getUserNotifications(user.getId());
        return Result.success(notifications);
    }

    @GetMapping("/unread-count")
    public Result<Integer> getUnreadCount(HttpSession session) {
        User user = (User) session.getAttribute("user");
        int count = notificationService.getUnreadCount(user.getId());
        return Result.success(count);
    }

    @PutMapping("/{id}/read")
    public Result<?> markAsRead(@PathVariable Long id) {
        try {
            notificationService.markAsRead(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/read-all")
    public Result<?> markAllAsRead(HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            notificationService.markAllAsRead(user.getId());
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteNotification(@PathVariable Long id) {
        try {
            notificationService.deleteNotification(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

