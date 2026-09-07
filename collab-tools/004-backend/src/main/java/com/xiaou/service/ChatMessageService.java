package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.ChatMessage;
import com.xiaou.mapper.ChatMessageMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMessageService extends ServiceImpl<ChatMessageMapper, ChatMessage> {
    
    public Page<ChatMessage> getChatHistory(Long userId, Long friendId, Integer page, Integer size) {
        Page<ChatMessage> pageParam = new Page<>(page, size);
        return this.page(pageParam, new LambdaQueryWrapper<ChatMessage>()
                .and(wrapper -> wrapper
                        .and(w -> w.eq(ChatMessage::getFromUserId, userId).eq(ChatMessage::getToUserId, friendId))
                        .or(w -> w.eq(ChatMessage::getFromUserId, friendId).eq(ChatMessage::getToUserId, userId)))
                .orderByDesc(ChatMessage::getCreateTime));
    }
    
    public List<ChatMessage> getUnreadMessages(Long userId) {
        return this.list(new LambdaQueryWrapper<ChatMessage>()
                .eq(ChatMessage::getToUserId, userId)
                .eq(ChatMessage::getIsRead, 0)
                .orderByAsc(ChatMessage::getCreateTime));
    }
    
    public boolean markAsRead(Long messageId) {
        ChatMessage message = this.getById(messageId);
        if (message != null) {
            message.setIsRead(1);
            message.setReadTime(LocalDateTime.now());
            return this.updateById(message);
        }
        return false;
    }
    
    public boolean markAllAsRead(Long userId, Long fromUserId) {
        List<ChatMessage> messages = this.list(new LambdaQueryWrapper<ChatMessage>()
                .eq(ChatMessage::getToUserId, userId)
                .eq(ChatMessage::getFromUserId, fromUserId)
                .eq(ChatMessage::getIsRead, 0));
        
        for (ChatMessage message : messages) {
            message.setIsRead(1);
            message.setReadTime(LocalDateTime.now());
        }
        return this.updateBatchById(messages);
    }
    
    public Page<ChatMessage> searchMessages(Long userId, String keyword, Integer page, Integer size) {
        Page<ChatMessage> pageParam = new Page<>(page, size);
        return this.page(pageParam, new LambdaQueryWrapper<ChatMessage>()
                .and(wrapper -> wrapper
                        .eq(ChatMessage::getFromUserId, userId)
                        .or()
                        .eq(ChatMessage::getToUserId, userId))
                .like(ChatMessage::getContent, keyword)
                .orderByDesc(ChatMessage::getCreateTime));
    }
}

