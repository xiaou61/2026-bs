package com.xiaou.campusvideo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusvideo.entity.Video;
import com.xiaou.campusvideo.entity.VideoCollect;
import com.xiaou.campusvideo.mapper.VideoCollectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoCollectService extends ServiceImpl<VideoCollectMapper, VideoCollect> {
    
    @Autowired
    private VideoService videoService;
    
    @Transactional
    public void collect(Long userId, Long videoId) {
        if (isCollect(userId, videoId)) {
            return;
        }
        
        VideoCollect videoCollect = new VideoCollect();
        videoCollect.setUserId(userId);
        videoCollect.setVideoId(videoId);
        videoCollect.setFolderId(0L);
        this.save(videoCollect);
        
        Video video = videoService.getById(videoId);
        video.setCollectCount(video.getCollectCount() + 1);
        videoService.updateById(video);
        videoService.updateHeatScore(videoId);
    }
    
    @Transactional
    public void uncollect(Long userId, Long videoId) {
        LambdaQueryWrapper<VideoCollect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoCollect::getUserId, userId)
               .eq(VideoCollect::getVideoId, videoId);
        this.remove(wrapper);
        
        Video video = videoService.getById(videoId);
        if (video.getCollectCount() > 0) {
            video.setCollectCount(video.getCollectCount() - 1);
            videoService.updateById(video);
            videoService.updateHeatScore(videoId);
        }
    }
    
    public boolean isCollect(Long userId, Long videoId) {
        LambdaQueryWrapper<VideoCollect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoCollect::getUserId, userId)
               .eq(VideoCollect::getVideoId, videoId);
        return this.count(wrapper) > 0;
    }
}

