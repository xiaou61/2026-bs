package com.xiaou.herbal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.herbal.common.Constants;
import com.xiaou.herbal.entity.Topic;
import com.xiaou.herbal.mapper.TopicMapper;
import com.xiaou.herbal.service.TopicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {

    @Override
    @Transactional
    public void createTopic(Long userId, Topic topic) {
        topic.setAuthorId(userId);
        topic.setStatus(Constants.TopicStatus.PUBLISHED);
        topic.setViews(0);
        topic.setReplies(0);
        topic.setCreateTime(LocalDateTime.now());
        topic.setUpdateTime(LocalDateTime.now());
        baseMapper.insert(topic);
    }

    @Override
    public Topic getTopicDetail(Long topicId) {
        Topic topic = baseMapper.selectById(topicId);
        if (topic != null) {
            topic.setViews(topic.getViews() + 1);
            baseMapper.updateById(topic);
        }
        return topic;
    }

    @Override
    public List<Topic> listPublishedTopics() {
        LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Topic::getStatus, Constants.TopicStatus.PUBLISHED)
                .orderByDesc(Topic::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<Topic> searchTopics(String keyword) {
        LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Topic::getTitle, keyword)
                .or()
                .like(Topic::getContent, keyword)
                .eq(Topic::getStatus, Constants.TopicStatus.PUBLISHED);
        return baseMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public void updateTopic(Long topicId, Topic topic) {
        topic.setId(topicId);
        topic.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(topic);
    }

    @Override
    @Transactional
    public void deleteTopic(Long topicId) {
        baseMapper.deleteById(topicId);
    }

    @Override
    public List<Topic> getUserTopics(Long userId) {
        LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Topic::getAuthorId, userId).orderByDesc(Topic::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public void increaseReplies(Long topicId) {
        Topic topic = baseMapper.selectById(topicId);
        topic.setReplies(topic.getReplies() + 1);
        baseMapper.updateById(topic);
    }
}
