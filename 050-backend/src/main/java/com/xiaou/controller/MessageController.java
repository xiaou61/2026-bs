package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.entity.Message;
import com.xiaou.service.MessageService;
import com.xiaou.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/page")
    public Result<?> pageMessages(@RequestHeader("Authorization") String token,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer size,
                                  @RequestParam(required = false) Integer isRead) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        IPage<Message> result = messageService.pageUserMessages(page, size, userId, isRead);
        return Result.success(result);
    }

    @GetMapping("/unreadCount")
    public Result<?> getUnreadCount(@RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        Integer count = messageService.getUnreadCount(userId);
        return Result.success(count);
    }

    @PutMapping("/read/{id}")
    public Result<?> markAsRead(@RequestHeader("Authorization") String token,
                                @PathVariable Long id) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        messageService.markAsRead(id, userId);
        return Result.success("已读");
    }

    @PutMapping("/readAll")
    public Result<?> markAllAsRead(@RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        messageService.markAllAsRead(userId);
        return Result.success("全部已读");
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteMessage(@PathVariable Long id) {
        messageService.removeById(id);
        return Result.success("删除成功");
    }
}
