package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Message;

public interface MessageService extends IService<Message> {
    IPage<Message> pageUserMessages(Integer page, Integer size, Long userId, Integer isRead);
    void markAsRead(Long messageId, Long userId);
    void markAllAsRead(Long userId);
    Integer getUnreadCount(Long userId);
    void sendMessage(Long userId, Integer type, String title, String content);
}
