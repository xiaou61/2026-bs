package com.xiaou.campusvideo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusvideo.entity.Comment;
import com.xiaou.campusvideo.entity.CommentLike;
import com.xiaou.campusvideo.mapper.CommentLikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentLikeService extends ServiceImpl<CommentLikeMapper, CommentLike> {
    
    @Autowired
    @Lazy
    private CommentService commentService;
    
    @Transactional
    public void like(Long userId, Long commentId) {
        if (isLike(userId, commentId)) {
            return;
        }
        
        CommentLike commentLike = new CommentLike();
        commentLike.setUserId(userId);
        commentLike.setCommentId(commentId);
        this.save(commentLike);
        
        Comment comment = commentService.getById(commentId);
        comment.setLikeCount(comment.getLikeCount() + 1);
        commentService.updateById(comment);
    }
    
    @Transactional
    public void unlike(Long userId, Long commentId) {
        LambdaQueryWrapper<CommentLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommentLike::getUserId, userId)
               .eq(CommentLike::getCommentId, commentId);
        this.remove(wrapper);
        
        Comment comment = commentService.getById(commentId);
        if (comment.getLikeCount() > 0) {
            comment.setLikeCount(comment.getLikeCount() - 1);
            commentService.updateById(comment);
        }
    }
    
    public boolean isLike(Long userId, Long commentId) {
        LambdaQueryWrapper<CommentLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommentLike::getUserId, userId)
               .eq(CommentLike::getCommentId, commentId);
        return this.count(wrapper) > 0;
    }
}

