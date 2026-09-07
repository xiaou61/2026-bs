package com.xiaou.collabboard.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.collabboard.entity.Notification;
import com.xiaou.collabboard.service.NotificationService;
import com.xiaou.collabboard.util.Result;
import com.xiaou.collabboard.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/list")
    public Result<IPage<Notification>> getNotificationList(
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "20") Integer pageSize) {

        Long userId = UserHolder.get();
        IPage<Notification> page = notificationService.getNotificationList(userId, type, pageNum, pageSize);
        return Result.success(page);
    }

    @GetMapping("/unread-count")
    public Result<Long> getUnreadCount() {
        Long userId = UserHolder.get();
        Long count = notificationService.getUnreadCount(userId);
        return Result.success(count);
    }

    @PutMapping("/{id}/read")
    public Result<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return Result.success();
    }

    @PutMapping("/read-all")
    public Result<Void> markAllAsRead() {
        Long userId = UserHolder.get();
        notificationService.markAllAsRead(userId);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteNotification(@PathVariable Long id) {
        notificationService.removeById(id);
        return Result.success();
    }
}

