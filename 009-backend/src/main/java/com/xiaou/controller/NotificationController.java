package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.Notification;
import com.xiaou.service.NotificationService;
import com.xiaou.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
@CrossOrigin
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/list")
    public Result<?> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestHeader("Authorization") String token) {
        try {
            Long userId = JwtUtil.getUserId(token);
            
            Page<Notification> pageObj = new Page<>(page, size);
            QueryWrapper<Notification> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            wrapper.orderByDesc("create_time");
            
            Page<Notification> result = notificationService.page(pageObj, wrapper);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("查询失败");
        }
    }

    @GetMapping("/unread-count")
    public Result<?> unreadCount(@RequestHeader("Authorization") String token) {
        try {
            Long userId = JwtUtil.getUserId(token);
            
            QueryWrapper<Notification> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            wrapper.eq("is_read", 0);
            
            long count = notificationService.count(wrapper);
            return Result.success(count);
        } catch (Exception e) {
            return Result.error("查询失败");
        }
    }

    @PutMapping("/{id}/read")
    public Result<?> markRead(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            Long userId = JwtUtil.getUserId(token);
            
            Notification notification = notificationService.getById(id);
            if (notification == null) {
                return Result.error("通知不存在");
            }
            
            if (!notification.getUserId().equals(userId)) {
                return Result.error("无权操作");
            }
            
            notification.setIsRead(1);
            notificationService.updateById(notification);
            
            return Result.success("标记成功");
        } catch (Exception e) {
            return Result.error("操作失败");
        }
    }

    @PutMapping("/read-all")
    public Result<?> markAllRead(@RequestHeader("Authorization") String token) {
        try {
            Long userId = JwtUtil.getUserId(token);
            
            QueryWrapper<Notification> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            wrapper.eq("is_read", 0);
            
            Notification notification = new Notification();
            notification.setIsRead(1);
            notificationService.update(notification, wrapper);
            
            return Result.success("全部标记已读");
        } catch (Exception e) {
            return Result.error("操作失败");
        }
    }
}

