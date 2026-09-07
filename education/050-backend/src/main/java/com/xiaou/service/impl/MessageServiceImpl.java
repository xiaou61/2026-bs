package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Message;
import com.xiaou.mapper.MessageMapper;
import com.xiaou.service.MessageService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    public IPage<Message> pageUserMessages(Integer page, Integer size, Long userId, Integer isRead) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getUserId, userId);
        if (isRead != null) {
            wrapper.eq(Message::getIsRead, isRead);
        }
        wrapper.orderByDesc(Message::getCreateTime);
        return this.page(new Page<>(page, size), wrapper);
    }

    @Override
    public void markAsRead(Long messageId, Long userId) {
        this.update(new LambdaUpdateWrapper<Message>()
                .eq(Message::getId, messageId)
                .eq(Message::getUserId, userId)
                .set(Message::getIsRead, 1));
    }

    @Override
    public void markAllAsRead(Long userId) {
        this.update(new LambdaUpdateWrapper<Message>()
                .eq(Message::getUserId, userId)
                .eq(Message::getIsRead, 0)
                .set(Message::getIsRead, 1));
    }

    @Override
    public Integer getUnreadCount(Long userId) {
        return Math.toIntExact(this.count(new LambdaQueryWrapper<Message>()
                .eq(Message::getUserId, userId)
                .eq(Message::getIsRead, 0)));
    }

    @Override
    public void sendMessage(Long userId, Integer type, String title, String content) {
        Message message = new Message();
        message.setUserId(userId);
        message.setType(type);
        message.setTitle(title);
        message.setContent(content);
        message.setIsRead(0);
        message.setCreateTime(LocalDateTime.now());
        this.save(message);
    }
}
