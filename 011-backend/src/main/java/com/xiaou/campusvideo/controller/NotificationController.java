package com.xiaou.campusvideo.controller;

import com.xiaou.campusvideo.service.NotificationService;
import com.xiaou.campusvideo.util.Result;
import com.xiaou.campusvideo.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;
    
    @GetMapping("/list")
    public Result<?> getNotificationList() {
        Long userId = UserHolder.getUserId();
        return Result.success(notificationService.getNotificationList(userId));
    }
    
    @GetMapping("/unread-count")
    public Result<Long> getUnreadCount() {
        Long userId = UserHolder.getUserId();
        long count = notificationService.getUnreadCount(userId);
        return Result.success(count);
    }
    
    @PutMapping("/{id}/read")
    public Result<?> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return Result.success("已读");
    }
    
    @PutMapping("/read-all")
    public Result<?> markAllAsRead() {
        Long userId = UserHolder.getUserId();
        notificationService.markAllAsRead(userId);
        return Result.success("全部已读");
    }
}

