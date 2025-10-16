package com.xiaou.campusshare.controller;

import com.xiaou.campusshare.common.Result;
import com.xiaou.campusshare.entity.Notification;
import com.xiaou.campusshare.service.NotificationService;
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
    public Result<List<Notification>> getList(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<Notification> notifications = notificationService.getUserNotifications(userId);
        return Result.success(notifications);
    }

    @GetMapping("/unread-count")
    public Result<Long> getUnreadCount(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        long count = notificationService.getUnreadCount(userId);
        return Result.success(count);
    }

    @PutMapping("/{id}/read")
    public Result<?> markRead(@PathVariable Long id) {
        Notification notification = notificationService.getById(id);
        if (notification != null) {
            notification.setIsRead(1);
            notificationService.updateById(notification);
        }
        return Result.success("标记成功");
    }

    @PutMapping("/read-all")
    public Result<?> readAll(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<Notification> notifications = notificationService.getUserNotifications(userId);
        for (Notification notification : notifications) {
            if (notification.getIsRead() == 0) {
                notification.setIsRead(1);
                notificationService.updateById(notification);
            }
        }
        return Result.success("全部标记成功");
    }
}

