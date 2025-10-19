package com.xiaou.confession.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.confession.entity.Comment;
import com.xiaou.confession.entity.LikeRecord;
import com.xiaou.confession.entity.Post;
import com.xiaou.confession.entity.User;
import com.xiaou.confession.mapper.CommentMapper;
import com.xiaou.confession.mapper.LikeRecordMapper;
import com.xiaou.confession.mapper.PostMapper;
import com.xiaou.confession.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeService {
    
    private final LikeRecordMapper likeRecordMapper;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;
    
    @Transactional
    public boolean toggleLike(Long userId, Long targetId, Integer targetType) {
        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId);
        wrapper.eq(LikeRecord::getTargetId, targetId);
        wrapper.eq(LikeRecord::getTargetType, targetType);
        
        LikeRecord existingLike = likeRecordMapper.selectOne(wrapper);
        
        if (existingLike != null) {
            likeRecordMapper.deleteById(existingLike.getId());
            
            if (targetType == 1) {
                Post post = postMapper.selectById(targetId);
                if (post != null) {
                    post.setLikeCount(Math.max(0, post.getLikeCount() - 1));
                    postMapper.updateById(post);
                    
                    User author = userMapper.selectById(post.getUserId());
                    if (author != null) {
                        author.setLikeCount(Math.max(0, author.getLikeCount() - 1));
                        author.setPoints(Math.max(0, author.getPoints() - 1));
                        userMapper.updateById(author);
                    }
                }
            } else if (targetType == 2) {
                Comment comment = commentMapper.selectById(targetId);
                if (comment != null) {
                    comment.setLikeCount(Math.max(0, comment.getLikeCount() - 1));
                    commentMapper.updateById(comment);
                }
            }
            
            return false;
        } else {
            LikeRecord likeRecord = new LikeRecord();
            likeRecord.setUserId(userId);
            likeRecord.setTargetId(targetId);
            likeRecord.setTargetType(targetType);
            likeRecord.setCreateTime(LocalDateTime.now());
            
            likeRecordMapper.insert(likeRecord);
            
            if (targetType == 1) {
                Post post = postMapper.selectById(targetId);
                if (post != null) {
                    post.setLikeCount(post.getLikeCount() + 1);
                    postMapper.updateById(post);
                    
                    User author = userMapper.selectById(post.getUserId());
                    if (author != null) {
                        author.setLikeCount(author.getLikeCount() + 1);
                        author.setPoints(author.getPoints() + 1);
                        userMapper.updateById(author);
                    }
                }
            } else if (targetType == 2) {
                Comment comment = commentMapper.selectById(targetId);
                if (comment != null) {
                    comment.setLikeCount(comment.getLikeCount() + 1);
                    commentMapper.updateById(comment);
                }
            }
            
            return true;
        }
    }
    
    public boolean hasLiked(Long userId, Long targetId, Integer targetType) {
        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId);
        wrapper.eq(LikeRecord::getTargetId, targetId);
        wrapper.eq(LikeRecord::getTargetType, targetType);
        
        return likeRecordMapper.selectCount(wrapper) > 0;
    }
}

