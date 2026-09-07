package com.blog.controller;

import com.blog.common.BusinessException;
import com.blog.common.Result;
import com.blog.entity.Comment;
import com.blog.service.CommentService;
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
import java.util.Map;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @GetMapping("/post-page")
    public Result<?> postPage(@RequestParam Long postId,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(commentService.postPage(postId, pageNum, pageSize));
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long postId,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) String keyword,
                          HttpServletRequest request) {
        return Result.success(commentService.page(pageNum, pageSize, postId, status, keyword,
                (String) request.getAttribute("role"), (Long) request.getAttribute("userId")));
    }

    @PostMapping
    public Result<?> add(@RequestBody Comment comment, HttpServletRequest request) {
        commentService.add(comment, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @PutMapping("/review")
    public Result<?> review(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("status") == null) {
            throw new BusinessException("参数不完整");
        }
        Long id = ((Number) params.get("id")).longValue();
        Integer status = ((Number) params.get("status")).intValue();
        String replyContent = params.get("replyContent") == null ? null : params.get("replyContent").toString();
        commentService.review(id, status, replyContent);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        commentService.deleteById(id, (Long) request.getAttribute("userId"), (String) request.getAttribute("role"));
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
