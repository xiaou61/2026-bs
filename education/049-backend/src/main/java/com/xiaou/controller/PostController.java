package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.Post;
import com.xiaou.entity.Comment;
import com.xiaou.entity.User;
import com.xiaou.mapper.PostMapper;
import com.xiaou.mapper.CommentMapper;
import com.xiaou.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") int current,
                         @RequestParam(defaultValue = "10") int size,
                         @RequestParam(required = false) Long subjectId) {
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getStatus, 1);
        if (subjectId != null) wrapper.eq(Post::getSubjectId, subjectId);
        wrapper.orderByDesc(Post::getIsTop).orderByDesc(Post::getCreateTime);
        Page<Post> page = postMapper.selectPage(new Page<>(current, size), wrapper);
        page.getRecords().forEach(this::fillPostInfo);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        Post post = postMapper.selectById(id);
        if (post != null) {
            fillPostInfo(post);
            post.setViewCount(post.getViewCount() + 1);
            postMapper.updateById(post);
        }
        return Result.success(post);
    }

    @PostMapping
    public Result<?> create(@RequestBody Post post, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        post.setUserId(userId);
        post.setViewCount(0);
        post.setLikeCount(0);
        post.setCommentCount(0);
        post.setStatus(1);
        postMapper.insert(post);
        return Result.success();
    }

    @PostMapping("/{id}/like")
    public Result<?> like(@PathVariable Long id) {
        Post post = postMapper.selectById(id);
        if (post != null) {
            post.setLikeCount(post.getLikeCount() + 1);
            postMapper.updateById(post);
        }
        return Result.success();
    }

    @GetMapping("/{id}/comments")
    public Result<?> getComments(@PathVariable Long id) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getPostId, id).orderByAsc(Comment::getCreateTime);
        return Result.success(commentMapper.selectList(wrapper));
    }

    @PostMapping("/{id}/comment")
    public Result<?> addComment(@PathVariable Long id, @RequestBody Comment comment, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        comment.setUserId(userId);
        comment.setPostId(id);
        comment.setLikeCount(0);
        commentMapper.insert(comment);
        
        Post post = postMapper.selectById(id);
        post.setCommentCount(post.getCommentCount() + 1);
        postMapper.updateById(post);
        return Result.success();
    }

    private void fillPostInfo(Post post) {
        User user = userMapper.selectById(post.getUserId());
        if (user != null) {
            post.setAuthorName(user.getNickname());
            post.setAuthorAvatar(user.getAvatar());
        }
    }
}
