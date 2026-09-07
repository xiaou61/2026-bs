package com.xiaou.herbal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.herbal.entity.Topic;

import java.util.List;

public interface TopicService extends IService<Topic> {

    void createTopic(Long userId, Topic topic);

    Topic getTopicDetail(Long topicId);

    List<Topic> listPublishedTopics();

    List<Topic> searchTopics(String keyword);

    void updateTopic(Long topicId, Topic topic);

    void deleteTopic(Long topicId);

    List<Topic> getUserTopics(Long userId);

    void increaseReplies(Long topicId);
}
