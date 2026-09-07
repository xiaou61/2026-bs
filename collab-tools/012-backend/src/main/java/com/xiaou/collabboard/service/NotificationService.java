package com.xiaou.collabboard.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.collabboard.entity.Notification;
import com.xiaou.collabboard.mapper.NotificationMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService extends ServiceImpl<NotificationMapper, Notification> {

    public void sendNotification(Long userId, Long fromUserId, String type, String title, String content, Long relatedId, String relatedType) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setFromUserId(fromUserId);
        notification.setType(type);
        notification.setTitle(title);
        notification.setContent(content);
        notification.setRelatedId(relatedId);
        notification.setRelatedType(relatedType);
        notification.setIsRead(0);
        notification.setCreateTime(LocalDateTime.now());

        baseMapper.insert(notification);
    }

    public IPage<Notification> getNotificationList(Long userId, String type, Integer pageNum, Integer pageSize) {
        Page<Notification> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Notification> wrapper = new LambdaQueryWrapper<>();
        
        wrapper.eq(Notification::getUserId, userId);

        if (type != null) {
            wrapper.eq(Notification::getType, type);
        }

        wrapper.orderByDesc(Notification::getCreateTime);
        return baseMapper.selectPage(page, wrapper);
    }

    public Long getUnreadCount(Long userId) {
        LambdaQueryWrapper<Notification> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notification::getUserId, userId)
                .eq(Notification::getIsRead, 0);
        return baseMapper.selectCount(wrapper);
    }

    public void markAsRead(Long notificationId) {
        Notification notification = baseMapper.selectById(notificationId);
        if (notification != null) {
            notification.setIsRead(1);
            baseMapper.updateById(notification);
        }
    }

    public void markAllAsRead(Long userId) {
        LambdaQueryWrapper<Notification> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notification::getUserId, userId)
                .eq(Notification::getIsRead, 0);
        
        Notification update = new Notification();
        update.setIsRead(1);
        baseMapper.update(update, wrapper);
    }
}

