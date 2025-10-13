package com.xiaou.campusvideo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.campusvideo.entity.Comment;
import com.xiaou.campusvideo.service.CommentLikeService;
import com.xiaou.campusvideo.service.CommentService;
import com.xiaou.campusvideo.util.Result;
import com.xiaou.campusvideo.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private CommentLikeService commentLikeService;
    
    @PostMapping("/publish")
    public Result<?> publishComment(@RequestBody Comment comment) {
        comment.setUserId(UserHolder.getUserId());
        commentService.publishComment(comment);
        return Result.success("评论成功");
    }
    
    @GetMapping("/list")
    public Result<IPage<Comment>> getCommentList(
            @RequestParam Long videoId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        
        Page<Comment> commentPage = new Page<>(page, size);
        IPage<Comment> result = commentService.getCommentList(commentPage, videoId);
        return Result.success(result);
    }
    
    @DeleteMapping("/{id}")
    public Result<?> deleteComment(@PathVariable Long id) {
        Long userId = UserHolder.getUserId();
        Comment comment = commentService.getById(id);
        
        if (!comment.getUserId().equals(userId)) {
            return Result.error("无权限删除");
        }
        
        comment.setStatus(2);
        commentService.updateById(comment);
        return Result.success("删除成功");
    }
    
    @PostMapping("/{id}/like")
    public Result<?> likeComment(@PathVariable Long id) {
        Long userId = UserHolder.getUserId();
        commentLikeService.like(userId, id);
        return Result.success("点赞成功");
    }
    
    @DeleteMapping("/{id}/like")
    public Result<?> unlikeComment(@PathVariable Long id) {
        Long userId = UserHolder.getUserId();
        commentLikeService.unlike(userId, id);
        return Result.success("取消点赞成功");
    }
    
    @GetMapping("/{id}/replies")
    public Result<List<Comment>> getReplies(@PathVariable Long id) {
        List<Comment> replies = commentService.getReplies(id);
        return Result.success(replies);
    }
}

