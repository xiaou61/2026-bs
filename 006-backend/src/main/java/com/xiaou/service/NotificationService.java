package com.xiaou.service;

import com.xiaou.entity.Notification;
import com.xiaou.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public List<Notification> getUserNotifications(Long userId) {
        return notificationMapper.findByUserId(userId);
    }

    public int getUnreadCount(Long userId) {
        return notificationMapper.countUnread(userId);
    }

    public void markAsRead(Long id) {
        notificationMapper.updateRead(id);
    }

    public void markAllAsRead(Long userId) {
        notificationMapper.updateAllRead(userId);
    }

    public void deleteNotification(Long id) {
        notificationMapper.deleteById(id);
    }

    public void createNotification(Notification notification) {
        if (notification.getIsRead() == null) {
            notification.setIsRead(0);
        }
        notificationMapper.insert(notification);
    }
}

