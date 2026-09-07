package com.ticket.controller;

import com.ticket.common.Result;
import com.ticket.entity.Comment;
import com.ticket.service.CommentService;
import com.ticket.utils.AuthUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @GetMapping("/public/movie/{movieId}")
    public Result<?> publicList(@PathVariable Long movieId,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(commentService.publicPage(movieId, pageNum, pageSize));
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long movieId,
                          @RequestParam(required = false) String status,
                          HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(commentService.page(movieId, status, pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result<?> add(@RequestBody Comment comment, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        commentService.add(userId, comment);
        return Result.success();
    }

    @PutMapping("/audit/{id}/{status}")
    public Result<?> audit(@PathVariable Long id, @PathVariable String status, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        commentService.audit(id, status);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        commentService.deleteById(id, userId, role);
        return Result.success();
    }
}
