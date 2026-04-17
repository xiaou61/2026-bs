package com.xiaou.confession.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.confession.common.Result;
import com.xiaou.confession.entity.Comment;
import com.xiaou.confession.entity.Post;
import com.xiaou.confession.service.CommentService;
import com.xiaou.confession.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;
    private final CommentService commentService;
    
    @PostMapping
    public Result<Post> createPost(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            String category = (String) params.get("category");
            String title = (String) params.get("title");
            String content = (String) params.get("content");
            String images = (String) params.get("images");
            String tags = (String) params.get("tags");
            
            Post post = postService.createPost(userId, category, title, content, images, tags);
            return Result.success(post);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping
    public Result<IPage<Post>> getPostList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "NEW") String orderBy) {
        try {
            IPage<Post> posts = postService.getPostList(page, size, category, orderBy);
            return Result.success(posts);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Result<Post> getPostDetail(@PathVariable Long id) {
        try {
            Post post = postService.getPostDetail(id);
            return Result.success(post);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/search")
    public Result<IPage<Post>> searchPosts(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam String keyword) {
        try {
            IPage<Post> posts = postService.searchPosts(page, size, keyword);
            return Result.success(posts);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/my")
    public Result<IPage<Post>> getMyPosts(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            IPage<Post> posts = postService.getMyPosts(userId, page, size);
            return Result.success(posts);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{postId}/comments")
    public Result<IPage<Comment>> getPostComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "NEW") String orderBy) {
        try {
            IPage<Comment> comments = commentService.getCommentThreadByPostId(postId, page, size, orderBy);
            return Result.success(comments);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/{postId}/comments")
    public Result<Comment> createPostComment(
            @PathVariable Long postId,
            @RequestBody Map<String, Object> params,
            HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            Long parentId = params.get("parentId") != null ? Long.valueOf(params.get("parentId").toString()) : null;
            String replyToNickname = (String) params.get("replyToNickname");
            String content = (String) params.get("content");

            Comment comment = commentService.createComment(userId, postId, parentId, replyToNickname, content);
            return Result.success(comment);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<Void> deletePost(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            postService.deletePost(id, userId);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

