package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Notification;
import com.xiaou.mapper.NotificationMapper;
import org.springframework.stereotype.Service;

@Service
public class NotificationService extends ServiceImpl<NotificationMapper, Notification> {
}

