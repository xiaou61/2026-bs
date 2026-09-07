package com.xiaou.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.CurrentUser;
import com.xiaou.common.R;
import com.xiaou.entity.Notification;
import com.xiaou.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notify")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;
    
    @GetMapping("/list")
    public R getNotifications(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "20") Integer size) {
        Long userId = CurrentUser.get();
        Page<Notification> notifications = notificationService.getNotifications(userId, page, size);
        return R.ok(notifications);
    }
    
    @GetMapping("/unread")
    public R getUnreadNotifications() {
        Long userId = CurrentUser.get();
        List<Notification> notifications = notificationService.getUnreadNotifications(userId);
        return R.ok(notifications);
    }
    
    @GetMapping("/unread/count")
    public R getUnreadCount() {
        Long userId = CurrentUser.get();
        Long count = notificationService.getUnreadCount(userId);
        return R.ok(count);
    }
    
    @PutMapping("/read/{id}")
    public R markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return R.ok("已读");
    }
    
    @PutMapping("/read/all")
    public R markAllAsRead() {
        Long userId = CurrentUser.get();
        notificationService.markAllAsRead(userId);
        return R.ok("全部已读");
    }
}

