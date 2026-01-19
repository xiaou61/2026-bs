package com.rental.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rental.common.Result;
import com.rental.entity.Message;
import com.rental.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 消息通知控制器
 */
@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 获取消息列表
     */
    @GetMapping("/list")
    public Result<IPage<Message>> getList(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer isRead) {
        Long userId = (Long) request.getAttribute("userId");
        IPage<Message> result = messageService.getList(userId, page, size, isRead);
        return Result.success(result);
    }

    /**
     * 标记已读
     */
    @PutMapping("/{id}/read")
    public Result<?> markRead(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        messageService.markRead(userId, id);
        return Result.success();
    }

    /**
     * 全部已读
     */
    @PutMapping("/read-all")
    public Result<?> markAllRead(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        messageService.markAllRead(userId);
        return Result.success();
    }

    /**
     * 获取未读数量
     */
    @GetMapping("/unread-count")
    public Result<Integer> getUnreadCount(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        int count = messageService.getUnreadCount(userId);
        return Result.success(count);
    }
}
