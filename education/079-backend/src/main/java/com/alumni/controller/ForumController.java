package com.alumni.controller;

import com.alumni.common.Result;
import com.alumni.entity.ForumCategory;
import com.alumni.entity.ForumPost;
import com.alumni.entity.ForumReply;
import com.alumni.service.ForumService;
import com.alumni.utils.AuthUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @GetMapping("/category/list")
    public Result<List<ForumCategory>> categoryList() {
        return Result.success(forumService.categoryList());
    }

    @PostMapping("/category")
    public Result<?> addCategory(@RequestBody ForumCategory category, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        forumService.addCategory(category);
        return Result.success();
    }

    @PutMapping("/category")
    public Result<?> updateCategory(@RequestBody ForumCategory category, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        forumService.updateCategory(category);
        return Result.success();
    }

    @DeleteMapping("/category/{id}")
    public Result<?> deleteCategory(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        forumService.deleteCategory(id);
        return Result.success();
    }

    @GetMapping("/post/list")
    public Result<Page<ForumPost>> postList(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            Long categoryId, String title, HttpServletRequest request) {
        Long userId = request.getAttribute("userId") == null ? null : AuthUtils.getUserId(request);
        return Result.success(forumService.postList(pageNum, pageSize, categoryId, title, userId));
    }

    @GetMapping("/post/{id}")
    public Result<ForumPost> getPostById(@PathVariable Long id, HttpServletRequest request) {
        Long userId = request.getAttribute("userId") == null ? null : AuthUtils.getUserId(request);
        return Result.success(forumService.getPostById(id, userId));
    }

    @PostMapping("/post")
    public Result<?> addPost(@RequestBody ForumPost post, HttpServletRequest request) {
        Long userId = AuthUtils.getUserId(request);
        post.setUserId(userId);
        forumService.addPost(post);
        return Result.success();
    }

    @PutMapping("/post")
    public Result<?> updatePost(@RequestBody ForumPost post, HttpServletRequest request) {
        forumService.updatePost(post, AuthUtils.getUserId(request), AuthUtils.isAdmin(request));
        return Result.success();
    }

    @DeleteMapping("/post/{id}")
    public Result<?> deletePost(@PathVariable Long id, HttpServletRequest request) {
        forumService.deletePost(id, AuthUtils.getUserId(request), AuthUtils.isAdmin(request));
        return Result.success();
    }

    @GetMapping("/post/{id}/replies")
    public Result<List<ForumReply>> getPostReplies(@PathVariable Long id) {
        return Result.success(forumService.getPostReplies(id));
    }

    @PostMapping("/post/{id}/reply")
    public Result<?> addReply(@PathVariable Long id, @RequestBody ForumReply reply, HttpServletRequest request) {
        Long userId = AuthUtils.getUserId(request);
        reply.setPostId(id);
        reply.setUserId(userId);
        forumService.addReply(reply);
        return Result.success();
    }

    @DeleteMapping("/reply/{id}")
    public Result<?> deleteReply(@PathVariable Long id, HttpServletRequest request) {
        forumService.deleteReply(id, AuthUtils.getUserId(request), AuthUtils.isAdmin(request));
        return Result.success();
    }

    @PostMapping("/post/{id}/like")
    public Result<?> like(@PathVariable Long id, HttpServletRequest request) {
        Long userId = AuthUtils.getUserId(request);
        forumService.like(id, userId);
        return Result.success();
    }

    @DeleteMapping("/post/{id}/like")
    public Result<?> unlike(@PathVariable Long id, HttpServletRequest request) {
        Long userId = AuthUtils.getUserId(request);
        forumService.unlike(id, userId);
        return Result.success();
    }
}
