package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.FeedbackReview;
import com.timebank.mapper.FeedbackReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackReviewService extends BaseCrudService<FeedbackReview> {
    private final FeedbackReviewMapper feedbackReviewMapper;

    @Override
    protected BaseMapper<FeedbackReview> mapper() {
        return feedbackReviewMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"feedback_no", "project_name", "feedback_type", "review_target"};
    }
}

