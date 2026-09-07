package com.milk.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.milk.entity.Notification;
import com.milk.mapper.NotificationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NotificationService {

    @Resource
    private NotificationMapper notificationMapper;

    public List<Notification> listByUserId(Long userId) {
        return notificationMapper.selectList(new QueryWrapper<Notification>().eq("user_id", userId).orderByDesc("create_time"));
    }

    public void read(Long id) {
        notificationMapper.update(null, new UpdateWrapper<Notification>().eq("id", id).set("is_read", 1));
    }

    public void readAll(Long userId) {
        notificationMapper.update(null, new UpdateWrapper<Notification>().eq("user_id", userId).set("is_read", 1));
    }

    public void send(Notification notification) {
        notificationMapper.insert(notification);
    }
}
