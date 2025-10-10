package com.xiaou.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.CurrentUser;
import com.xiaou.common.R;
import com.xiaou.entity.ChatMessage;
import com.xiaou.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    
    @Autowired
    private ChatMessageService chatMessageService;
    
    @GetMapping("/history")
    public R getChatHistory(@RequestParam Long friendId,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "20") Integer size) {
        Long userId = CurrentUser.get();
        Page<ChatMessage> history = chatMessageService.getChatHistory(userId, friendId, page, size);
        return R.ok(history);
    }
    
    @GetMapping("/unread")
    public R getUnreadMessages() {
        Long userId = CurrentUser.get();
        List<ChatMessage> messages = chatMessageService.getUnreadMessages(userId);
        return R.ok(messages);
    }
    
    @PutMapping("/read/{messageId}")
    public R markAsRead(@PathVariable Long messageId) {
        chatMessageService.markAsRead(messageId);
        return R.ok("已读");
    }
    
    @PutMapping("/read/all/{fromUserId}")
    public R markAllAsRead(@PathVariable Long fromUserId) {
        Long userId = CurrentUser.get();
        chatMessageService.markAllAsRead(userId, fromUserId);
        return R.ok("已读");
    }
    
    @GetMapping("/search")
    public R searchMessages(@RequestParam String keyword,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "20") Integer size) {
        Long userId = CurrentUser.get();
        Page<ChatMessage> result = chatMessageService.searchMessages(userId, keyword, page, size);
        return R.ok(result);
    }
}

