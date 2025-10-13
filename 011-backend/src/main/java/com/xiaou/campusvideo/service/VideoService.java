package com.xiaou.campusvideo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusvideo.entity.*;
import com.xiaou.campusvideo.mapper.VideoMapper;
import com.xiaou.campusvideo.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoService extends ServiceImpl<VideoMapper, Video> {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private VideoTopicService videoTopicService;
    
    @Autowired
    private TopicService topicService;
    
    @Autowired
    @Lazy
    private VideoLikeService videoLikeService;
    
    @Autowired
    @Lazy
    private VideoCollectService videoCollectService;
    
    @Autowired
    @Lazy
    private UserFollowService userFollowService;
    
    @Autowired
    private UserPointsLogService userPointsLogService;
    
    @Transactional
    public void publishVideo(Video video, List<Long> topicIds) {
        video.setStatus(1);
        video.setPlayCount(0);
        video.setLikeCount(0);
        video.setCommentCount(0);
        video.setShareCount(0);
        video.setCollectCount(0);
        video.setHeatScore(BigDecimal.ZERO);
        video.setIsTop(0);
        
        this.save(video);
        
        if (topicIds != null && !topicIds.isEmpty()) {
            for (Long topicId : topicIds) {
                VideoTopic videoTopic = new VideoTopic();
                videoTopic.setVideoId(video.getId());
                videoTopic.setTopicId(topicId);
                videoTopicService.save(videoTopic);
                
                topicService.increaseVideoCount(topicId);
            }
        }
        
        User user = userService.getById(video.getUserId());
        user.setVideoCount(user.getVideoCount() + 1);
        userService.updateById(user);
        
        userPointsLogService.addPoints(video.getUserId(), 10, "VIDEO", "发布视频", video.getId());
    }
    
    public IPage<Video> getRecommendVideos(Page<Video> page) {
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Video::getStatus, 1)
               .orderByDesc(Video::getHeatScore)
               .orderByDesc(Video::getPublishTime);
        
        IPage<Video> videoPage = this.page(page, wrapper);
        fillVideoInfo(videoPage.getRecords());
        
        return videoPage;
    }
    
    public IPage<Video> getFollowingVideos(Page<Video> page, Long userId) {
        List<UserFollow> follows = userFollowService.getFollowingList(userId);
        if (follows.isEmpty()) {
            return new Page<>();
        }
        
        List<Long> followUserIds = follows.stream()
                .map(UserFollow::getFollowUserId)
                .collect(Collectors.toList());
        
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Video::getUserId, followUserIds)
               .eq(Video::getStatus, 1)
               .orderByDesc(Video::getPublishTime);
        
        IPage<Video> videoPage = this.page(page, wrapper);
        fillVideoInfo(videoPage.getRecords());
        
        return videoPage;
    }
    
    public Video getVideoDetail(Long videoId) {
        Video video = this.getById(videoId);
        if (video == null) {
            return null;
        }
        
        video.setUser(userService.getById(video.getUserId()));
        
        List<VideoTopic> videoTopics = videoTopicService.getByVideoId(videoId);
        if (!videoTopics.isEmpty()) {
            List<Long> topicIds = videoTopics.stream()
                    .map(VideoTopic::getTopicId)
                    .collect(Collectors.toList());
            video.setTopics(topicService.listByIds(topicIds));
        }
        
        Long currentUserId = UserHolder.getUserId();
        if (currentUserId != null) {
            video.setIsLike(videoLikeService.isLike(currentUserId, videoId));
            video.setIsCollect(videoCollectService.isCollect(currentUserId, videoId));
            video.getUser().setIsFollow(userFollowService.isFollow(currentUserId, video.getUserId()));
        }
        
        return video;
    }
    
    private void fillVideoInfo(List<Video> videos) {
        for (Video video : videos) {
            video.setUser(userService.getById(video.getUserId()));
            
            Long currentUserId = UserHolder.getUserId();
            if (currentUserId != null) {
                video.setIsLike(videoLikeService.isLike(currentUserId, video.getId()));
                video.setIsCollect(videoCollectService.isCollect(currentUserId, video.getId()));
                video.getUser().setIsFollow(userFollowService.isFollow(currentUserId, video.getUserId()));
            }
        }
    }
    
    public void updateHeatScore(Long videoId) {
        Video video = this.getById(videoId);
        if (video == null) {
            return;
        }
        
        long hoursSincePublish = (System.currentTimeMillis() - video.getPublishTime().toEpochSecond(java.time.ZoneOffset.ofHours(8)) * 1000) / 3600000;
        double timeDecay = 1.0 / (1.0 + hoursSincePublish / 24.0);
        
        double heatScore = video.getLikeCount() * 4 +
                          video.getCommentCount() * 6 +
                          video.getShareCount() * 8 +
                          video.getCollectCount() * 5 +
                          video.getPlayCount() * 0.5 +
                          timeDecay * 100;
        
        video.setHeatScore(BigDecimal.valueOf(heatScore));
        this.updateById(video);
    }
    
    public void increasePlayCount(Long videoId) {
        Video video = this.getById(videoId);
        if (video != null) {
            video.setPlayCount(video.getPlayCount() + 1);
            this.updateById(video);
            updateHeatScore(videoId);
        }
    }
    
    public IPage<Video> getUserLikedVideos(Long userId, Integer page, Integer size) {
        List<VideoLike> likes = videoLikeService.lambdaQuery()
                .eq(VideoLike::getUserId, userId)
                .orderByDesc(VideoLike::getCreateTime)
                .list();
        
        if (likes.isEmpty()) {
            return new Page<>();
        }
        
        List<Long> videoIds = likes.stream()
                .map(VideoLike::getVideoId)
                .collect(Collectors.toList());
        
        Page<Video> videoPage = new Page<>(page, size);
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Video::getId, videoIds)
               .eq(Video::getStatus, 1);
        
        IPage<Video> result = this.page(videoPage, wrapper);
        fillVideoInfo(result.getRecords());
        
        return result;
    }
    
    public IPage<Video> getUserCollectedVideos(Long userId, Integer page, Integer size) {
        List<VideoCollect> collects = videoCollectService.lambdaQuery()
                .eq(VideoCollect::getUserId, userId)
                .orderByDesc(VideoCollect::getCreateTime)
                .list();
        
        if (collects.isEmpty()) {
            return new Page<>();
        }
        
        List<Long> videoIds = collects.stream()
                .map(VideoCollect::getVideoId)
                .collect(Collectors.toList());
        
        Page<Video> videoPage = new Page<>(page, size);
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Video::getId, videoIds)
               .eq(Video::getStatus, 1);
        
        IPage<Video> result = this.page(videoPage, wrapper);
        fillVideoInfo(result.getRecords());
        
        return result;
    }
}


