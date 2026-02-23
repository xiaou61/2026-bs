package com.ticket.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.Result;
import com.ticket.entity.Comment;
import com.ticket.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/movie/{movieId}")
    public Result<Page<Comment>> getMovieComments(
            @PathVariable Long movieId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(commentService.getMovieComments(movieId, pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result<Void> addComment(@RequestBody Comment comment, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        commentService.addComment(comment, userId);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteComment(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        commentService.deleteComment(id, userId);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<Page<Comment>> listComments(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String status) {
        return Result.success(commentService.listComments(pageNum, pageSize, status));
    }

    @PutMapping("/status/{id}")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam String status) {
        commentService.updateStatus(id, status);
        return Result.success();
    }
}
