package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.FeedbackReview;
import com.timebank.mapper.FeedbackReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackReviewService extends BaseCrudService<FeedbackReview> {
    private final FeedbackReviewMapper outboundRecordMapper;

    @Override
    protected BaseMapper<FeedbackReview> mapper() {
        return outboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"outbound_no", "consumable_name", "lab_name", "outbound_qty"};
    }
}





