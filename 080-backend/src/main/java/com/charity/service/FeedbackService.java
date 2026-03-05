package com.charity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.entity.Feedback;
import com.charity.mapper.FeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    public Page<Feedback> getList(int pageNum, int pageSize, String feedbackType, Long childId) {
        Page<Feedback> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Feedback> wrapper = new QueryWrapper<>();
        if (feedbackType != null && !feedbackType.isEmpty()) {
            wrapper.eq("feedback_type", feedbackType);
        }
        if (childId != null) {
            wrapper.eq("child_id", childId);
        }
        wrapper.orderByDesc("create_time");
        return feedbackMapper.selectPage(page, wrapper);
    }

    public void add(Feedback feedback) {
        feedbackMapper.insert(feedback);
    }
}
