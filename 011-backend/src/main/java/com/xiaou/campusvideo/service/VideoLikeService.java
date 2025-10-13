package com.xiaou.campusvideo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusvideo.entity.Video;
import com.xiaou.campusvideo.entity.VideoLike;
import com.xiaou.campusvideo.mapper.VideoLikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoLikeService extends ServiceImpl<VideoLikeMapper, VideoLike> {
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private UserPointsLogService userPointsLogService;
    
    @Transactional
    public void like(Long userId, Long videoId) {
        if (isLike(userId, videoId)) {
            return;
        }
        
        VideoLike videoLike = new VideoLike();
        videoLike.setUserId(userId);
        videoLike.setVideoId(videoId);
        this.save(videoLike);
        
        Video video = videoService.getById(videoId);
        video.setLikeCount(video.getLikeCount() + 1);
        videoService.updateById(video);
        
        videoService.updateHeatScore(videoId);
        
        if (!userId.equals(video.getUserId())) {
            notificationService.sendNotification(
                video.getUserId(),
                userId,
                "LIKE",
                "点赞了你的视频",
                videoId
            );
        }
        
        userPointsLogService.addPoints(video.getUserId(), 2, "LIKE", "视频被点赞", videoId);
    }
    
    @Transactional
    public void unlike(Long userId, Long videoId) {
        LambdaQueryWrapper<VideoLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoLike::getUserId, userId)
               .eq(VideoLike::getVideoId, videoId);
        this.remove(wrapper);
        
        Video video = videoService.getById(videoId);
        if (video.getLikeCount() > 0) {
            video.setLikeCount(video.getLikeCount() - 1);
            videoService.updateById(video);
            videoService.updateHeatScore(videoId);
        }
    }
    
    public boolean isLike(Long userId, Long videoId) {
        LambdaQueryWrapper<VideoLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoLike::getUserId, userId)
               .eq(VideoLike::getVideoId, videoId);
        return this.count(wrapper) > 0;
    }
}

