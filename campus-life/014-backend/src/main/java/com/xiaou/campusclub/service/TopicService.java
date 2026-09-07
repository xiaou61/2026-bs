package com.xiaou.campusclub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.campusclub.dto.CommentRequest;
import com.xiaou.campusclub.dto.TopicRequest;
import com.xiaou.campusclub.vo.CommentVO;
import com.xiaou.campusclub.vo.TopicVO;

import java.util.List;

public interface TopicService {
    IPage<TopicVO> getTopicList(Integer page, Integer size, Long clubId, Long circleId, Long userId);
    TopicVO getTopicDetail(Long topicId, Long userId);
    Long createTopic(Long userId, TopicRequest request);
    void updateTopic(Long topicId, Long userId, TopicRequest request);
    void deleteTopic(Long topicId, Long userId);
    void likeTopic(Long topicId, Long userId);
    void unlikeTopic(Long topicId, Long userId);
    List<CommentVO> getTopicComments(Long topicId, Long userId);
    Long addComment(Long userId, CommentRequest request);
    void likeComment(Long commentId, Long userId);
}

