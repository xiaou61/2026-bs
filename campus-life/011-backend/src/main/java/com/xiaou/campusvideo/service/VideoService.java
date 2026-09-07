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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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
        video.setPublishTime(LocalDateTime.now());
        
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
        normalizePublishTime(video);
        
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
            normalizePublishTime(video);
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
        
        LocalDateTime publishTime = resolvePublishTime(video);
        long hoursSincePublish = (System.currentTimeMillis() - publishTime.toEpochSecond(java.time.ZoneOffset.ofHours(8)) * 1000) / 3600000;
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
    
    public List<Video> getRelatedVideos(Long videoId, Integer size) {
        int limit = (size == null || size <= 0) ? 6 : size;
        Video currentVideo = this.getById(videoId);
        if (currentVideo == null) {
            return new ArrayList<>();
        }
        
        LinkedHashSet<Long> relatedIds = new LinkedHashSet<>();
        List<VideoTopic> currentTopics = videoTopicService.getByVideoId(videoId);
        for (VideoTopic videoTopic : currentTopics) {
            List<VideoTopic> topicVideos = videoTopicService.getByTopicId(videoTopic.getTopicId());
            for (VideoTopic topicVideo : topicVideos) {
                if (!videoId.equals(topicVideo.getVideoId())) {
                    relatedIds.add(topicVideo.getVideoId());
                    if (relatedIds.size() >= limit) {
                        break;
                    }
                }
            }
            if (relatedIds.size() >= limit) {
                break;
            }
        }
        
        if (relatedIds.size() < limit) {
            List<Video> sameAuthorVideos = this.lambdaQuery()
                    .eq(Video::getUserId, currentVideo.getUserId())
                    .eq(Video::getStatus, 1)
                    .ne(Video::getId, videoId)
                    .orderByDesc(Video::getPublishTime)
                    .last("LIMIT " + limit)
                    .list();
            for (Video video : sameAuthorVideos) {
                relatedIds.add(video.getId());
                if (relatedIds.size() >= limit) {
                    break;
                }
            }
        }
        
        if (relatedIds.size() < limit) {
            List<Video> hotVideos = this.lambdaQuery()
                    .eq(Video::getStatus, 1)
                    .ne(Video::getId, videoId)
                    .orderByDesc(Video::getHeatScore)
                    .orderByDesc(Video::getPublishTime)
                    .last("LIMIT " + (limit * 2))
                    .list();
            for (Video video : hotVideos) {
                relatedIds.add(video.getId());
                if (relatedIds.size() >= limit) {
                    break;
                }
            }
        }
        
        List<Long> orderedIds = relatedIds.stream().limit(limit).collect(Collectors.toList());
        if (orderedIds.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Video> relatedVideos = this.lambdaQuery()
                .in(Video::getId, orderedIds)
                .eq(Video::getStatus, 1)
                .list();
        Map<Long, Integer> orderMap = new HashMap<>();
        for (int i = 0; i < orderedIds.size(); i++) {
            orderMap.put(orderedIds.get(i), i);
        }
        relatedVideos.sort(Comparator.comparingInt(video -> orderMap.getOrDefault(video.getId(), Integer.MAX_VALUE)));
        fillVideoInfo(relatedVideos);
        
        return relatedVideos;
    }

    public void normalizePublishTimes(List<Video> videos) {
        if (videos == null) {
            return;
        }
        for (Video video : videos) {
            normalizePublishTime(video);
        }
    }

    public LocalDateTime resolvePublishTime(Video video) {
        if (video == null) {
            return LocalDateTime.now();
        }
        if (video.getPublishTime() != null) {
            return video.getPublishTime();
        }
        if (video.getCreateTime() != null) {
            return video.getCreateTime();
        }
        return LocalDateTime.now();
    }

    private void normalizePublishTime(Video video) {
        if (video != null && video.getPublishTime() == null) {
            video.setPublishTime(resolvePublishTime(video));
        }
    }
}


