package com.xiaou.campusvideo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.campusvideo.entity.Topic;
import com.xiaou.campusvideo.entity.Video;
import com.xiaou.campusvideo.entity.VideoTopic;
import com.xiaou.campusvideo.service.TopicService;
import com.xiaou.campusvideo.service.VideoService;
import com.xiaou.campusvideo.service.VideoTopicService;
import com.xiaou.campusvideo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/topic")
public class TopicController {
    
    @Autowired
    private TopicService topicService;
    
    @Autowired
    private VideoTopicService videoTopicService;
    
    @Autowired
    private VideoService videoService;
    
    @GetMapping("/hot")
    public Result<List<Topic>> getHotTopics() {
        List<Topic> topics = topicService.getHotTopics();
        return Result.success(topics);
    }
    
    @GetMapping("/recommend")
    public Result<List<Topic>> getRecommendTopics() {
        List<Topic> topics = topicService.getRecommendTopics();
        return Result.success(topics);
    }
    
    @GetMapping("/{id}")
    public Result<Topic> getTopicDetail(@PathVariable Long id) {
        Topic topic = topicService.getById(id);
        if (topic == null) {
            return Result.error("话题不存在");
        }
        topicService.increaseViewCount(id);
        return Result.success(topic);
    }
    
    @GetMapping("/{id}/videos")
    public Result<IPage<Video>> getTopicVideos(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        
        List<VideoTopic> videoTopics = videoTopicService.getByTopicId(id);
        if (videoTopics.isEmpty()) {
            return Result.success(new Page<>());
        }
        
        List<Long> videoIds = videoTopics.stream()
                .map(VideoTopic::getVideoId)
                .collect(Collectors.toList());
        
        Page<Video> videoPage = new Page<>(page, size);
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Video::getId, videoIds)
               .eq(Video::getStatus, 1)
               .orderByDesc(Video::getPublishTime);
        
        IPage<Video> result = videoService.page(videoPage, wrapper);
        return Result.success(result);
    }
    
    @GetMapping("/search")
    public Result<List<Topic>> searchTopics(@RequestParam String keyword) {
        LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Topic::getTopicName, keyword)
               .eq(Topic::getStatus, 1)
               .orderByDesc(Topic::getHeatScore);
        
        List<Topic> topics = topicService.list(wrapper);
        return Result.success(topics);
    }
}

