package com.xiaou.campusclub.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.campusclub.dto.CommentRequest;
import com.xiaou.campusclub.dto.TopicRequest;
import com.xiaou.campusclub.entity.*;
import com.xiaou.campusclub.exception.BusinessException;
import com.xiaou.campusclub.mapper.*;
import com.xiaou.campusclub.service.TopicService;
import com.xiaou.campusclub.service.UserService;
import com.xiaou.campusclub.vo.CommentVO;
import com.xiaou.campusclub.vo.TopicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {
    
    @Autowired
    private TopicMapper topicMapper;
    
    @Autowired
    private TopicCommentMapper commentMapper;
    
    @Autowired
    private LikeRecordMapper likeRecordMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private ClubMapper clubMapper;
    
    @Autowired
    private CircleMapper circleMapper;
    
    @Autowired
    private UserService userService;
    
    @Override
    public IPage<TopicVO> getTopicList(Integer page, Integer size, Long clubId, Long circleId, Long userId) {
        Page<Topic> topicPage = new Page<>(page, size);
        LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Topic::getStatus, 0);
        
        if (clubId != null) {
            wrapper.eq(Topic::getClubId, clubId);
        }
        
        if (circleId != null) {
            wrapper.eq(Topic::getCircleId, circleId);
        }
        
        wrapper.orderByDesc(Topic::getIsTop)
               .orderByDesc(Topic::getCreateTime);
        
        IPage<Topic> result = topicMapper.selectPage(topicPage, wrapper);
        
        return result.convert(topic -> convertToVO(topic, userId));
    }
    
    @Override
    @Transactional
    public TopicVO getTopicDetail(Long topicId, Long userId) {
        Topic topic = topicMapper.selectById(topicId);
        if (topic == null || topic.getStatus() == 1) {
            throw new BusinessException("话题不存在");
        }
        
        topic.setViewCount(topic.getViewCount() + 1);
        topicMapper.updateById(topic);
        
        return convertToVO(topic, userId);
    }
    
    @Override
    @Transactional
    public Long createTopic(Long userId, TopicRequest request) {
        Topic topic = new Topic();
        topic.setUserId(userId);
        topic.setClubId(request.getClubId());
        topic.setCircleId(request.getCircleId());
        topic.setTitle(request.getTitle());
        topic.setContent(request.getContent());
        topic.setImages(request.getImages());
        topic.setViewCount(0);
        topic.setLikeCount(0);
        topic.setCommentCount(0);
        topic.setIsTop(0);
        topic.setStatus(0);
        topic.setCreateTime(LocalDateTime.now());
        
        topicMapper.insert(topic);
        
        userService.addPoints(userId, 3, "PUBLISH_TOPIC", "发布话题");
        
        if (request.getCircleId() != null) {
            Circle circle = circleMapper.selectById(request.getCircleId());
            if (circle != null) {
                circle.setTopicCount(circle.getTopicCount() + 1);
                circleMapper.updateById(circle);
            }
        }
        
        return topic.getId();
    }
    
    @Override
    @Transactional
    public void updateTopic(Long topicId, Long userId, TopicRequest request) {
        Topic topic = topicMapper.selectById(topicId);
        if (topic == null) {
            throw new BusinessException("话题不存在");
        }
        
        if (!topic.getUserId().equals(userId)) {
            throw new BusinessException("无权限操作");
        }
        
        topic.setTitle(request.getTitle());
        topic.setContent(request.getContent());
        topic.setImages(request.getImages());
        topic.setUpdateTime(LocalDateTime.now());
        
        topicMapper.updateById(topic);
    }
    
    @Override
    @Transactional
    public void deleteTopic(Long topicId, Long userId) {
        Topic topic = topicMapper.selectById(topicId);
        if (topic == null) {
            throw new BusinessException("话题不存在");
        }
        
        if (!topic.getUserId().equals(userId)) {
            throw new BusinessException("无权限操作");
        }
        
        topic.setStatus(1);
        topic.setUpdateTime(LocalDateTime.now());
        topicMapper.updateById(topic);
    }
    
    @Override
    @Transactional
    public void likeTopic(Long topicId, Long userId) {
        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId)
               .eq(LikeRecord::getTargetId, topicId)
               .eq(LikeRecord::getTargetType, 1);
        
        if (likeRecordMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("已点赞");
        }
        
        LikeRecord record = new LikeRecord();
        record.setUserId(userId);
        record.setTargetId(topicId);
        record.setTargetType(1);
        record.setCreateTime(LocalDateTime.now());
        likeRecordMapper.insert(record);
        
        Topic topic = topicMapper.selectById(topicId);
        topic.setLikeCount(topic.getLikeCount() + 1);
        topicMapper.updateById(topic);
    }
    
    @Override
    @Transactional
    public void unlikeTopic(Long topicId, Long userId) {
        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId)
               .eq(LikeRecord::getTargetId, topicId)
               .eq(LikeRecord::getTargetType, 1);
        likeRecordMapper.delete(wrapper);
        
        Topic topic = topicMapper.selectById(topicId);
        if (topic.getLikeCount() > 0) {
            topic.setLikeCount(topic.getLikeCount() - 1);
            topicMapper.updateById(topic);
        }
    }
    
    @Override
    public List<CommentVO> getTopicComments(Long topicId, Long userId) {
        LambdaQueryWrapper<TopicComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TopicComment::getTopicId, topicId)
               .eq(TopicComment::getParentId, 0)
               .orderByDesc(TopicComment::getCreateTime);
        List<TopicComment> comments = commentMapper.selectList(wrapper);
        
        return comments.stream()
                .map(comment -> convertCommentToVO(comment, userId))
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public Long addComment(Long userId, CommentRequest request) {
        TopicComment comment = new TopicComment();
        comment.setTopicId(request.getTopicId());
        comment.setUserId(userId);
        comment.setParentId(request.getParentId() != null ? request.getParentId() : 0L);
        comment.setReplyToId(request.getReplyToId());
        comment.setContent(request.getContent());
        comment.setLikeCount(0);
        comment.setCreateTime(LocalDateTime.now());
        
        commentMapper.insert(comment);
        
        Topic topic = topicMapper.selectById(request.getTopicId());
        topic.setCommentCount(topic.getCommentCount() + 1);
        topicMapper.updateById(topic);
        
        userService.addPoints(userId, 1, "COMMENT", "发表评论");
        
        return comment.getId();
    }
    
    @Override
    @Transactional
    public void likeComment(Long commentId, Long userId) {
        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId)
               .eq(LikeRecord::getTargetId, commentId)
               .eq(LikeRecord::getTargetType, 2);
        
        if (likeRecordMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("已点赞");
        }
        
        LikeRecord record = new LikeRecord();
        record.setUserId(userId);
        record.setTargetId(commentId);
        record.setTargetType(2);
        record.setCreateTime(LocalDateTime.now());
        likeRecordMapper.insert(record);
        
        TopicComment comment = commentMapper.selectById(commentId);
        comment.setLikeCount(comment.getLikeCount() + 1);
        commentMapper.updateById(comment);
    }
    
    private TopicVO convertToVO(Topic topic, Long userId) {
        TopicVO vo = BeanUtil.copyProperties(topic, TopicVO.class);
        
        User user = userMapper.selectById(topic.getUserId());
        if (user != null) {
            vo.setUsername(user.getUsername());
            vo.setAvatar(user.getAvatar());
        }
        
        if (topic.getClubId() != null) {
            Club club = clubMapper.selectById(topic.getClubId());
            if (club != null) {
                vo.setClubName(club.getName());
            }
        }
        
        if (topic.getCircleId() != null) {
            Circle circle = circleMapper.selectById(topic.getCircleId());
            if (circle != null) {
                vo.setCircleName(circle.getName());
            }
        }
        
        if (topic.getImages() != null && !topic.getImages().isEmpty()) {
            vo.setImages(JSONUtil.toList(topic.getImages(), String.class));
        }
        
        if (userId != null) {
            LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(LikeRecord::getUserId, userId)
                   .eq(LikeRecord::getTargetId, topic.getId())
                   .eq(LikeRecord::getTargetType, 1);
            vo.setIsLiked(likeRecordMapper.selectCount(wrapper) > 0);
        }
        
        return vo;
    }
    
    private CommentVO convertCommentToVO(TopicComment comment, Long userId) {
        CommentVO vo = BeanUtil.copyProperties(comment, CommentVO.class);
        
        User user = userMapper.selectById(comment.getUserId());
        if (user != null) {
            vo.setUsername(user.getUsername());
            vo.setAvatar(user.getAvatar());
        }
        
        if (comment.getReplyToId() != null) {
            User replyToUser = userMapper.selectById(comment.getReplyToId());
            if (replyToUser != null) {
                vo.setReplyToUsername(replyToUser.getUsername());
            }
        }
        
        if (userId != null) {
            LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(LikeRecord::getUserId, userId)
                   .eq(LikeRecord::getTargetId, comment.getId())
                   .eq(LikeRecord::getTargetType, 2);
            vo.setIsLiked(likeRecordMapper.selectCount(wrapper) > 0);
        }
        
        LambdaQueryWrapper<TopicComment> replyWrapper = new LambdaQueryWrapper<>();
        replyWrapper.eq(TopicComment::getParentId, comment.getId())
                    .orderByAsc(TopicComment::getCreateTime);
        List<TopicComment> replies = commentMapper.selectList(replyWrapper);
        vo.setReplies(replies.stream()
                .map(reply -> convertCommentToVO(reply, userId))
                .collect(Collectors.toList()));
        
        return vo;
    }
}

