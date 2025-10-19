package com.xiaou.confession.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.confession.entity.Message;
import com.xiaou.confession.entity.User;
import com.xiaou.confession.mapper.MessageMapper;
import com.xiaou.confession.mapper.UserMapper;
import com.xiaou.confession.util.AnonymousNicknameGenerator;
import com.xiaou.confession.util.SensitiveWordFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    
    private final MessageMapper messageMapper;
    private final UserMapper userMapper;
    private final SensitiveWordFilter sensitiveWordFilter;
    
    @Transactional
    public Message sendMessage(Long senderId, Long receiverId, String content) {
        User sender = userMapper.selectById(senderId);
        User receiver = userMapper.selectById(receiverId);
        
        if (sender == null || receiver == null) {
            throw new RuntimeException("用户不存在");
        }
        
        if (sender.getStatus() == 1) {
            throw new RuntimeException("您已被禁言，无法发送私信");
        }
        
        String filteredContent = sensitiveWordFilter.filterSensitiveWord(content, "*");
        
        String conversationId = generateConversationId(senderId, receiverId);
        
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getConversationId, conversationId)
                .orderByDesc(Message::getCreateTime)
                .last("LIMIT 1");
        Message lastMessage = messageMapper.selectOne(wrapper);
        
        String senderNickname;
        String receiverNickname;
        
        if (lastMessage != null) {
            if (lastMessage.getSenderId().equals(senderId)) {
                senderNickname = lastMessage.getSenderNickname();
                receiverNickname = lastMessage.getReceiverNickname();
            } else {
                senderNickname = lastMessage.getReceiverNickname();
                receiverNickname = lastMessage.getSenderNickname();
            }
        } else {
            senderNickname = AnonymousNicknameGenerator.generate();
            receiverNickname = AnonymousNicknameGenerator.generate();
        }
        
        Message message = new Message();
        message.setConversationId(conversationId);
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setSenderNickname(senderNickname);
        message.setReceiverNickname(receiverNickname);
        message.setContent(filteredContent);
        message.setIsRead(0);
        message.setCreateTime(LocalDateTime.now());
        
        messageMapper.insert(message);
        return message;
    }
    
    public Map<String, Object> getConversations(Long userId, Integer page, Integer size) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.eq(Message::getSenderId, userId).or().eq(Message::getReceiverId, userId));
        wrapper.orderByDesc(Message::getCreateTime);
        
        List<Message> allMessages = messageMapper.selectList(wrapper);
        
        Map<String, Message> conversationMap = new LinkedHashMap<>();
        Map<String, Integer> unreadCountMap = new HashMap<>();
        
        for (Message message : allMessages) {
            String conversationId = message.getConversationId();
            
            if (!conversationMap.containsKey(conversationId)) {
                conversationMap.put(conversationId, message);
            }
            
            if (message.getReceiverId().equals(userId) && message.getIsRead() == 0) {
                unreadCountMap.put(conversationId, unreadCountMap.getOrDefault(conversationId, 0) + 1);
            }
        }
        
        List<Map<String, Object>> conversations = new ArrayList<>();
        int start = (page - 1) * size;
        int end = Math.min(start + size, conversationMap.size());
        
        List<Message> conversationList = new ArrayList<>(conversationMap.values());
        for (int i = start; i < end; i++) {
            Message message = conversationList.get(i);
            Map<String, Object> conv = new HashMap<>();
            conv.put("conversationId", message.getConversationId());
            conv.put("lastMessage", message);
            conv.put("unreadCount", unreadCountMap.getOrDefault(message.getConversationId(), 0));
            
            Long otherUserId = message.getSenderId().equals(userId) ? 
                    message.getReceiverId() : message.getSenderId();
            String otherNickname = message.getSenderId().equals(userId) ? 
                    message.getReceiverNickname() : message.getSenderNickname();
            
            conv.put("otherUserId", otherUserId);
            conv.put("otherNickname", otherNickname);
            
            conversations.add(conv);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("conversations", conversations);
        result.put("total", conversationMap.size());
        result.put("page", page);
        result.put("size", size);
        
        return result;
    }
    
    public IPage<Message> getConversationMessages(String conversationId, Long userId, Integer page, Integer size) {
        Page<Message> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getConversationId, conversationId);
        wrapper.and(w -> w.eq(Message::getSenderId, userId).or().eq(Message::getReceiverId, userId));
        wrapper.orderByDesc(Message::getCreateTime);
        
        IPage<Message> messages = messageMapper.selectPage(pageParam, wrapper);
        
        List<Long> unreadMessageIds = messages.getRecords().stream()
                .filter(m -> m.getReceiverId().equals(userId) && m.getIsRead() == 0)
                .map(Message::getId)
                .collect(Collectors.toList());
        
        if (!unreadMessageIds.isEmpty()) {
            for (Long messageId : unreadMessageIds) {
                Message message = new Message();
                message.setId(messageId);
                message.setIsRead(1);
                messageMapper.updateById(message);
            }
        }
        
        return messages;
    }
    
    @Transactional
    public void markAsRead(Long messageId, Long userId) {
        Message message = messageMapper.selectById(messageId);
        if (message == null) {
            throw new RuntimeException("消息不存在");
        }
        
        if (!message.getReceiverId().equals(userId)) {
            throw new RuntimeException("无权操作");
        }
        
        message.setIsRead(1);
        messageMapper.updateById(message);
    }
    
    @Transactional
    public void deleteMessage(Long messageId, Long userId) {
        Message message = messageMapper.selectById(messageId);
        if (message == null) {
            throw new RuntimeException("消息不存在");
        }
        
        if (!message.getSenderId().equals(userId)) {
            throw new RuntimeException("只能撤回自己发送的消息");
        }
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createTime = message.getCreateTime();
        if (createTime.plusMinutes(2).isBefore(now)) {
            throw new RuntimeException("只能撤回2分钟内的消息");
        }
        
        messageMapper.deleteById(messageId);
    }
    
    @Transactional
    public void deleteConversation(String conversationId, Long userId) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getConversationId, conversationId);
        wrapper.and(w -> w.eq(Message::getSenderId, userId).or().eq(Message::getReceiverId, userId));
        
        messageMapper.delete(wrapper);
    }
    
    public Integer getUnreadCount(Long userId) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getReceiverId, userId);
        wrapper.eq(Message::getIsRead, 0);
        
        return messageMapper.selectCount(wrapper).intValue();
    }
    
    private String generateConversationId(Long userId1, Long userId2) {
        long smaller = Math.min(userId1, userId2);
        long larger = Math.max(userId1, userId2);
        return smaller + "_" + larger;
    }
}

