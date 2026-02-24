package com.bike.service;

import com.bike.entity.Feedback;
import com.bike.mapper.FeedbackMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    public void add(Feedback feedback) {
        feedbackMapper.insert(feedback);
    }

    public PageInfo<Feedback> getList(Integer pageNum, Integer pageSize, Integer status, Integer type) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(feedbackMapper.findList(status, type));
    }

    public PageInfo<Feedback> getMyList(Long userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(feedbackMapper.findByUserId(userId));
    }

    public void reply(Long id, String reply) {
        feedbackMapper.updateReply(id, reply);
    }
}
