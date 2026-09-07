package com.xiaou.herbal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.herbal.common.Constants;
import com.xiaou.herbal.entity.Comment;
import com.xiaou.herbal.mapper.CommentMapper;
import com.xiaou.herbal.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    @Transactional
    public void createComment(Long userId, Comment comment) {
        comment.setUserId(userId);
        comment.setStatus(Constants.CommentStatus.NORMAL);
        comment.setLikes(0);
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());
        baseMapper.insert(comment);
    }

    @Override
    public List<Comment> getCommentsByTarget(Integer targetType, Long targetId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getTargetType, targetType)
                .eq(Comment::getTargetId, targetId)
                .eq(Comment::getStatus, Constants.CommentStatus.NORMAL)
                .orderByDesc(Comment::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        baseMapper.deleteById(commentId);
    }

    @Override
    @Transactional
    public void likeComment(Long commentId) {
        Comment comment = baseMapper.selectById(commentId);
        comment.setLikes(comment.getLikes() + 1);
        baseMapper.updateById(comment);
    }

    @Override
    public List<Comment> getUserComments(Long userId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getUserId, userId).orderByDesc(Comment::getCreateTime);
        return baseMapper.selectList(wrapper);
    }
}
