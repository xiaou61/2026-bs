package com.xiaou.campusvideo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusvideo.entity.Notification;
import com.xiaou.campusvideo.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService extends ServiceImpl<NotificationMapper, Notification> {
    
    @Autowired
    private UserService userService;
    
    public void sendNotification(Long userId, Long fromUserId, String type, String content, Long relatedId) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setFromUserId(fromUserId);
        notification.setType(type);
        notification.setContent(content);
        notification.setRelatedId(relatedId);
        notification.setIsRead(0);
        this.save(notification);
    }
    
    public List<Notification> getNotificationList(Long userId) {
        LambdaQueryWrapper<Notification> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notification::getUserId, userId)
               .orderByDesc(Notification::getCreateTime);
        
        List<Notification> notifications = this.list(wrapper);
        for (Notification notification : notifications) {
            if (notification.getFromUserId() != null) {
                notification.setFromUser(userService.getById(notification.getFromUserId()));
            }
        }
        
        return notifications;
    }
    
    public long getUnreadCount(Long userId) {
        LambdaQueryWrapper<Notification> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notification::getUserId, userId)
               .eq(Notification::getIsRead, 0);
        return this.count(wrapper);
    }
    
    public void markAsRead(Long id) {
        Notification notification = this.getById(id);
        if (notification != null) {
            notification.setIsRead(1);
            this.updateById(notification);
        }
    }
    
    public void markAllAsRead(Long userId) {
        LambdaQueryWrapper<Notification> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notification::getUserId, userId)
               .eq(Notification::getIsRead, 0);
        
        List<Notification> notifications = this.list(wrapper);
        for (Notification notification : notifications) {
            notification.setIsRead(1);
            this.updateById(notification);
        }
    }
}

