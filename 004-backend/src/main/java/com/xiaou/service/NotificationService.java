package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Notification;
import com.xiaou.mapper.NotificationMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService extends ServiceImpl<NotificationMapper, Notification> {
    
    public Page<Notification> getNotifications(Long userId, Integer page, Integer size) {
        Page<Notification> pageParam = new Page<>(page, size);
        return this.page(pageParam, new LambdaQueryWrapper<Notification>()
                .eq(Notification::getToUserId, userId)
                .orderByDesc(Notification::getCreateTime));
    }
    
    public List<Notification> getUnreadNotifications(Long userId) {
        return this.list(new LambdaQueryWrapper<Notification>()
                .eq(Notification::getToUserId, userId)
                .eq(Notification::getIsRead, 0)
                .orderByDesc(Notification::getCreateTime));
    }
    
    public Long getUnreadCount(Long userId) {
        return this.count(new LambdaQueryWrapper<Notification>()
                .eq(Notification::getToUserId, userId)
                .eq(Notification::getIsRead, 0));
    }
    
    public boolean markAsRead(Long notificationId) {
        Notification notification = this.getById(notificationId);
        if (notification != null) {
            notification.setIsRead(1);
            notification.setReadTime(LocalDateTime.now());
            return this.updateById(notification);
        }
        return false;
    }
    
    public boolean markAllAsRead(Long userId) {
        List<Notification> notifications = this.list(new LambdaQueryWrapper<Notification>()
                .eq(Notification::getToUserId, userId)
                .eq(Notification::getIsRead, 0));
        
        for (Notification notification : notifications) {
            notification.setIsRead(1);
            notification.setReadTime(LocalDateTime.now());
        }
        return this.updateBatchById(notifications);
    }
}

