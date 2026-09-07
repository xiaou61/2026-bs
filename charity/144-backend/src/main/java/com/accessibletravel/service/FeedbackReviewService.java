package com.accessibletravel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.accessibletravel.entity.FeedbackReview;
import com.accessibletravel.mapper.FeedbackReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackReviewService {
    private final FeedbackReviewMapper feedbackReviewMapper;

    public PageInfo<FeedbackReview> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(feedbackReviewMapper.selectPage(keyword, status));
    }

    public void save(FeedbackReview entity) {
        if (entity.getId() == null) feedbackReviewMapper.insert(entity);
        else feedbackReviewMapper.update(entity);
    }

    public void delete(Long id) {
        feedbackReviewMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        feedbackReviewMapper.updateStatus(id, status);
    }
}

