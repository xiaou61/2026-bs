package com.rental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rental.entity.Message;
import com.rental.mapper.MessageMapper;
import com.rental.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void send(Long userId, String title, String content, String type) {
        Message message = new Message();
        message.setUserId(userId);
        message.setTitle(title);
        message.setContent(content);
        message.setType(type);
        message.setIsRead(0);
        messageMapper.insert(message);
    }

    @Override
    public IPage<Message> getList(Long userId, int page, int size, Integer isRead) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getUserId, userId);
        if (isRead != null) {
            wrapper.eq(Message::getIsRead, isRead);
        }
        wrapper.orderByDesc(Message::getCreateTime);
        return messageMapper.selectPage(new Page<>(page, size), wrapper);
    }

    @Override
    public void markRead(Long userId, Long messageId) {
        LambdaUpdateWrapper<Message> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Message::getId, messageId);
        wrapper.eq(Message::getUserId, userId);
        wrapper.set(Message::getIsRead, 1);
        messageMapper.update(null, wrapper);
    }

    @Override
    public void markAllRead(Long userId) {
        LambdaUpdateWrapper<Message> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Message::getUserId, userId);
        wrapper.eq(Message::getIsRead, 0);
        wrapper.set(Message::getIsRead, 1);
        messageMapper.update(null, wrapper);
    }

    @Override
    public int getUnreadCount(Long userId) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getUserId, userId);
        wrapper.eq(Message::getIsRead, 0);
        return Math.toIntExact(messageMapper.selectCount(wrapper));
    }
}
