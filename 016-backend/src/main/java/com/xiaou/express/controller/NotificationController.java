package com.xiaou.express.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.express.common.Result;
import com.xiaou.express.entity.Notification;
import com.xiaou.express.service.NotificationService;
import com.xiaou.express.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public Result<Page<Notification>> getNotifications(@RequestParam(defaultValue = "1") int pageNum,
                                                         @RequestParam(defaultValue = "10") int pageSize) {
        Long userId = UserContext.getCurrentUserId();
        Page<Notification> page = notificationService.getNotifications(userId, pageNum, pageSize);
        return Result.success(page);
    }

    @PutMapping("/{id}/read")
    public Result<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return Result.success();
    }

    @GetMapping("/unread-count")
    public Result<Long> getUnreadCount() {
        Long userId = UserContext.getCurrentUserId();
        Long count = notificationService.getUnreadCount(userId);
        return Result.success(count);
    }
}

