package com.xiaou.campusvideo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusvideo.entity.Topic;
import com.xiaou.campusvideo.mapper.TopicMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService extends ServiceImpl<TopicMapper, Topic> {
    
    public List<Topic> getHotTopics() {
        LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Topic::getStatus, 1)
               .eq(Topic::getIsHot, 1)
               .orderByDesc(Topic::getHeatScore)
               .orderByAsc(Topic::getSortOrder)
               .last("LIMIT 10");
        return this.list(wrapper);
    }
    
    public List<Topic> getRecommendTopics() {
        LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Topic::getStatus, 1)
               .orderByDesc(Topic::getVideoCount)
               .last("LIMIT 20");
        return this.list(wrapper);
    }
    
    public void increaseVideoCount(Long topicId) {
        Topic topic = this.getById(topicId);
        if (topic != null) {
            topic.setVideoCount(topic.getVideoCount() + 1);
            this.updateById(topic);
        }
    }
    
    public void increaseViewCount(Long topicId) {
        Topic topic = this.getById(topicId);
        if (topic != null) {
            topic.setViewCount(topic.getViewCount() + 1);
            this.updateById(topic);
        }
    }
}

