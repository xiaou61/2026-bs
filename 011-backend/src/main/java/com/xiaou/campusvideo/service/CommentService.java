package com.xiaou.campusvideo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusvideo.entity.Comment;
import com.xiaou.campusvideo.entity.Video;
import com.xiaou.campusvideo.mapper.CommentMapper;
import com.xiaou.campusvideo.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    @Lazy
    private VideoService videoService;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    @Lazy
    private CommentLikeService commentLikeService;
    
    @Autowired
    private UserPointsLogService userPointsLogService;
    
    @Transactional
    public void publishComment(Comment comment) {
        comment.setStatus(1);
        comment.setLikeCount(0);
        comment.setReplyCount(0);
        this.save(comment);
        
        Video video = videoService.getById(comment.getVideoId());
        video.setCommentCount(video.getCommentCount() + 1);
        videoService.updateById(video);
        videoService.updateHeatScore(comment.getVideoId());
        
        if (!comment.getUserId().equals(video.getUserId())) {
            notificationService.sendNotification(
                video.getUserId(),
                comment.getUserId(),
                "COMMENT",
                "评论了你的视频",
                comment.getVideoId()
            );
        }
        
        userPointsLogService.addPoints(video.getUserId(), 3, "COMMENT", "视频被评论", video.getId());
        
        if (comment.getParentId() != null && comment.getParentId() > 0) {
            Comment parentComment = this.getById(comment.getParentId());
            if (parentComment != null) {
                parentComment.setReplyCount(parentComment.getReplyCount() + 1);
                this.updateById(parentComment);
                
                if (!comment.getUserId().equals(parentComment.getUserId())) {
                    notificationService.sendNotification(
                        parentComment.getUserId(),
                        comment.getUserId(),
                        "COMMENT",
                        "回复了你的评论",
                        comment.getVideoId()
                    );
                }
            }
        }
    }
    
    public IPage<Comment> getCommentList(Page<Comment> page, Long videoId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getVideoId, videoId)
               .eq(Comment::getParentId, 0)
               .eq(Comment::getStatus, 1)
               .orderByDesc(Comment::getCreateTime);
        
        IPage<Comment> commentPage = this.page(page, wrapper);
        fillCommentInfo(commentPage.getRecords());
        
        return commentPage;
    }
    
    public List<Comment> getReplies(Long parentId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getParentId, parentId)
               .eq(Comment::getStatus, 1)
               .orderByAsc(Comment::getCreateTime);
        
        List<Comment> replies = this.list(wrapper);
        fillCommentInfo(replies);
        
        return replies;
    }
    
    private void fillCommentInfo(List<Comment> comments) {
        Long currentUserId = UserHolder.getUserId();
        
        for (Comment comment : comments) {
            comment.setUser(userService.getById(comment.getUserId()));
            
            if (comment.getReplyToUserId() != null) {
                comment.setReplyToUser(userService.getById(comment.getReplyToUserId()));
            }
            
            if (currentUserId != null) {
                comment.setIsLike(commentLikeService.isLike(currentUserId, comment.getId()));
            }
        }
    }
}

