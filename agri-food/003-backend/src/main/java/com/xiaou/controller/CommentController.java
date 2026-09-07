package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Comment;
import com.xiaou.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public Result<?> create(@RequestBody Comment comment, @RequestAttribute Long userId) {
        comment.setUserId(userId);
        commentService.addComment(comment);
        return Result.success("评论成功");
    }

    @GetMapping("/product/{productId}")
    public Result<?> getProductComments(@PathVariable Long productId) {
        List<Comment> comments = commentService.getProductComments(productId);
        return Result.success(comments);
    }
}

