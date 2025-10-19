package com.xiaou.confession.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.confession.common.Result;
import com.xiaou.confession.entity.Message;
import com.xiaou.confession.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    
    private final MessageService messageService;
    
    @PostMapping
    public Result<Message> sendMessage(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            Long senderId = (Long) request.getAttribute("userId");
            Long receiverId = Long.valueOf(params.get("receiverId").toString());
            String content = (String) params.get("content");
            
            Message message = messageService.sendMessage(senderId, receiverId, content);
            return Result.success(message);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/conversations")
    public Result<Map<String, Object>> getConversations(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            Map<String, Object> conversations = messageService.getConversations(userId, page, size);
            return Result.success(conversations);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/conversation/{conversationId}")
    public Result<IPage<Message>> getConversationMessages(
            @PathVariable String conversationId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "50") Integer size,
            HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            IPage<Message> messages = messageService.getConversationMessages(conversationId, userId, page, size);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PutMapping("/{id}/read")
    public Result<Void> markAsRead(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            messageService.markAsRead(id, userId);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<Void> deleteMessage(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            messageService.deleteMessage(id, userId);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @DeleteMapping("/conversations/{conversationId}")
    public Result<Void> deleteConversation(@PathVariable String conversationId, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            messageService.deleteConversation(conversationId, userId);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/unread-count")
    public Result<Integer> getUnreadCount(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            Integer count = messageService.getUnreadCount(userId);
            return Result.success(count);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

