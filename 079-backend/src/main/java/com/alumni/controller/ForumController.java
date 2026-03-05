package com.alumni.controller;

import com.alumni.common.Result;
import com.alumni.entity.ForumCategory;
import com.alumni.entity.ForumPost;
import com.alumni.entity.ForumReply;
import com.alumni.service.ForumService;
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
    public Result<?> addCategory(@RequestBody ForumCategory category) {
        forumService.addCategory(category);
        return Result.success();
    }

    @PutMapping("/category")
    public Result<?> updateCategory(@RequestBody ForumCategory category) {
        forumService.updateCategory(category);
        return Result.success();
    }

    @DeleteMapping("/category/{id}")
    public Result<?> deleteCategory(@PathVariable Long id) {
        forumService.deleteCategory(id);
        return Result.success();
    }

    @GetMapping("/post/list")
    public Result<Page<ForumPost>> postList(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            Long categoryId, String title, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(forumService.postList(pageNum, pageSize, categoryId, title, userId));
    }

    @GetMapping("/post/{id}")
    public Result<ForumPost> getPostById(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(forumService.getPostById(id, userId));
    }

    @PostMapping("/post")
    public Result<?> addPost(@RequestBody ForumPost post, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        post.setUserId(userId);
        forumService.addPost(post);
        return Result.success();
    }

    @PutMapping("/post")
    public Result<?> updatePost(@RequestBody ForumPost post) {
        forumService.updatePost(post);
        return Result.success();
    }

    @DeleteMapping("/post/{id}")
    public Result<?> deletePost(@PathVariable Long id) {
        forumService.deletePost(id);
        return Result.success();
    }

    @GetMapping("/post/{id}/replies")
    public Result<List<ForumReply>> getPostReplies(@PathVariable Long id) {
        return Result.success(forumService.getPostReplies(id));
    }

    @PostMapping("/post/{id}/reply")
    public Result<?> addReply(@PathVariable Long id, @RequestBody ForumReply reply, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        reply.setPostId(id);
        reply.setUserId(userId);
        forumService.addReply(reply);
        return Result.success();
    }

    @DeleteMapping("/reply/{id}")
    public Result<?> deleteReply(@PathVariable Long id) {
        forumService.deleteReply(id);
        return Result.success();
    }

    @PostMapping("/post/{id}/like")
    public Result<?> like(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        forumService.like(id, userId);
        return Result.success();
    }

    @DeleteMapping("/post/{id}/like")
    public Result<?> unlike(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        forumService.unlike(id, userId);
        return Result.success();
    }
}
