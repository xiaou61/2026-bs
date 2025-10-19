package com.xiaou.confession.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.confession.common.Result;
import com.xiaou.confession.entity.Comment;
import com.xiaou.confession.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    
    private final CommentService commentService;
    
    @PostMapping
    public Result<Comment> createComment(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            Long postId = Long.valueOf(params.get("postId").toString());
            Long parentId = params.get("parentId") != null ? Long.valueOf(params.get("parentId").toString()) : null;
            String replyToNickname = (String) params.get("replyToNickname");
            String content = (String) params.get("content");
            
            Comment comment = commentService.createComment(userId, postId, parentId, replyToNickname, content);
            return Result.success(comment);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/post/{postId}")
    public Result<IPage<Comment>> getCommentsByPostId(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "NEW") String orderBy) {
        try {
            IPage<Comment> comments = commentService.getCommentsByPostId(postId, page, size, orderBy);
            return Result.success(comments);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/replies/{parentId}")
    public Result<IPage<Comment>> getRepliesByParentId(
            @PathVariable Long parentId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        try {
            IPage<Comment> replies = commentService.getRepliesByParentId(parentId, page, size);
            return Result.success(replies);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<Void> deleteComment(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            commentService.deleteComment(id, userId);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

