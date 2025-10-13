package com.xiaou.campusvideo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusvideo.entity.VideoTopic;
import com.xiaou.campusvideo.mapper.VideoTopicMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoTopicService extends ServiceImpl<VideoTopicMapper, VideoTopic> {
    
    public List<VideoTopic> getByVideoId(Long videoId) {
        LambdaQueryWrapper<VideoTopic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoTopic::getVideoId, videoId);
        return this.list(wrapper);
    }
    
    public List<VideoTopic> getByTopicId(Long topicId) {
        LambdaQueryWrapper<VideoTopic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoTopic::getTopicId, topicId);
        return this.list(wrapper);
    }
}

