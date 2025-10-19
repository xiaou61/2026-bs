package com.xiaou.confession.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.confession.entity.Comment;
import com.xiaou.confession.entity.Post;
import com.xiaou.confession.entity.User;
import com.xiaou.confession.mapper.CommentMapper;
import com.xiaou.confession.mapper.PostMapper;
import com.xiaou.confession.mapper.UserMapper;
import com.xiaou.confession.util.AnonymousNicknameGenerator;
import com.xiaou.confession.util.SensitiveWordFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    
    private final CommentMapper commentMapper;
    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final SensitiveWordFilter sensitiveWordFilter;
    
    @Transactional
    public Comment createComment(Long userId, Long postId, Long parentId, String replyToNickname, String content) {
        Post post = postMapper.selectById(postId);
        if (post == null || post.getStatus() != 1) {
            throw new RuntimeException("帖子不存在");
        }
        
        String filteredContent = sensitiveWordFilter.filterSensitiveWord(content, "*");
        
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setAnonymousNickname(AnonymousNicknameGenerator.generate());
        comment.setAnonymousAvatar(AnonymousNicknameGenerator.generateAvatar());
        comment.setParentId(parentId == null ? 0L : parentId);
        comment.setReplyToNickname(replyToNickname);
        comment.setContent(filteredContent);
        comment.setLikeCount(0);
        comment.setIsAuthor(userId.equals(post.getUserId()) ? 1 : 0);
        comment.setStatus(0);
        comment.setCreateTime(LocalDateTime.now());
        
        if (parentId == null || parentId == 0) {
            LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Comment::getPostId, postId);
            wrapper.eq(Comment::getParentId, 0);
            Long count = commentMapper.selectCount(wrapper);
            comment.setFloorNumber(count.intValue() + 1);
        } else {
            Comment parentComment = commentMapper.selectById(parentId);
            if (parentComment != null && parentComment.getParentId() == 0) {
                comment.setFloorNumber(parentComment.getFloorNumber());
            }
        }
        
        commentMapper.insert(comment);
        
        post.setCommentCount(post.getCommentCount() + 1);
        postMapper.updateById(post);
        
        User user = userMapper.selectById(userId);
        user.setCommentCount(user.getCommentCount() + 1);
        user.setPoints(user.getPoints() + 2);
        userMapper.updateById(user);
        
        return comment;
    }
    
    public IPage<Comment> getCommentsByPostId(Long postId, Integer page, Integer size, String orderBy) {
        Page<Comment> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getPostId, postId);
        wrapper.eq(Comment::getStatus, 0);
        wrapper.eq(Comment::getParentId, 0);
        
        if ("HOT".equals(orderBy)) {
            wrapper.orderByDesc(Comment::getLikeCount);
        } else {
            wrapper.orderByAsc(Comment::getFloorNumber);
        }
        
        return commentMapper.selectPage(pageParam, wrapper);
    }
    
    public IPage<Comment> getRepliesByParentId(Long parentId, Integer page, Integer size) {
        Page<Comment> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getParentId, parentId);
        wrapper.eq(Comment::getStatus, 0);
        wrapper.orderByAsc(Comment::getCreateTime);
        
        return commentMapper.selectPage(pageParam, wrapper);
    }
    
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentMapper.selectById(commentId);
        if (comment == null) {
            throw new RuntimeException("评论不存在");
        }
        
        if (!comment.getUserId().equals(userId)) {
            throw new RuntimeException("无权删除");
        }
        
        comment.setStatus(1);
        commentMapper.updateById(comment);
        
        Post post = postMapper.selectById(comment.getPostId());
        if (post != null) {
            post.setCommentCount(Math.max(0, post.getCommentCount() - 1));
            postMapper.updateById(post);
        }
    }
}

