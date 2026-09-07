package com.rental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rental.entity.Message;

public interface MessageService {

    /**
     * 发送消息
     */
    void send(Long userId, String title, String content, String type);

    /**
     * 获取消息列表
     */
    IPage<Message> getList(Long userId, int page, int size, Integer isRead);

    /**
     * 标记已读
     */
    void markRead(Long userId, Long messageId);

    /**
     * 全部已读
     */
    void markAllRead(Long userId);

    /**
     * 获取未读数量
     */
    int getUnreadCount(Long userId);
}
