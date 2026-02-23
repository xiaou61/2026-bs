package com.blog.controller;

import com.blog.common.BusinessException;
import com.blog.common.Result;
import com.blog.entity.Post;
import com.blog.service.PostService;
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
@RequestMapping("/api/post")
public class PostController {

    @Resource
    private PostService postService;

    @GetMapping("/public-page")
    public Result<?> publicPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(required = false) String keyword,
                                @RequestParam(required = false) Long categoryId) {
        return Result.success(postService.publicPage(pageNum, pageSize, keyword, categoryId));
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String keyword,
                          @RequestParam(required = false) Long categoryId,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        return Result.success(postService.page(pageNum, pageSize, keyword, categoryId, status,
                (String) request.getAttribute("role"), (Long) request.getAttribute("userId")));
    }

    @GetMapping("/detail/{id}")
    public Result<?> detail(@PathVariable Long id) {
        return Result.success(postService.detail(id));
    }

    @PostMapping
    public Result<?> add(@RequestBody Post post, HttpServletRequest request) {
        postService.save(post, (Long) request.getAttribute("userId"), (String) request.getAttribute("role"));
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Post post, HttpServletRequest request) {
        postService.save(post, (Long) request.getAttribute("userId"), (String) request.getAttribute("role"));
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        if (params.get("id") == null || params.get("status") == null) {
            throw new BusinessException("参数不完整");
        }
        postService.updateStatus(((Number) params.get("id")).longValue(), ((Number) params.get("status")).intValue(),
                (Long) request.getAttribute("userId"), (String) request.getAttribute("role"));
        return Result.success();
    }

    @PutMapping("/top")
    public Result<?> updateTop(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("isTop") == null) {
            throw new BusinessException("参数不完整");
        }
        postService.updateTop(((Number) params.get("id")).longValue(), ((Number) params.get("isTop")).intValue());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        postService.deleteById(id, (Long) request.getAttribute("userId"), (String) request.getAttribute("role"));
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
