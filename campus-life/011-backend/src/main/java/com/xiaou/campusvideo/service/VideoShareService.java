package com.xiaou.campusvideo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusvideo.entity.Video;
import com.xiaou.campusvideo.entity.VideoShare;
import com.xiaou.campusvideo.mapper.VideoShareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoShareService extends ServiceImpl<VideoShareMapper, VideoShare> {
    
    @Autowired
    private VideoService videoService;
    
    @Transactional
    public void share(Long userId, Long videoId, String shareText) {
        VideoShare videoShare = new VideoShare();
        videoShare.setUserId(userId);
        videoShare.setVideoId(videoId);
        videoShare.setShareText(shareText);
        
        Video video = videoService.getById(videoId);
        videoShare.setOriginalUserId(video.getUserId());
        
        this.save(videoShare);
        
        video.setShareCount(video.getShareCount() + 1);
        videoService.updateById(video);
        videoService.updateHeatScore(videoId);
    }
}

