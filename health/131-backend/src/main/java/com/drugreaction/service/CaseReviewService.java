package com.drugreaction.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drugreaction.entity.CaseReview;
import com.drugreaction.mapper.CaseReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CaseReviewService extends BaseCrudService<CaseReview> {
    private final CaseReviewMapper caseReviewMapper;

    @Override
    protected BaseMapper<CaseReview> mapper() {
        return caseReviewMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"review_no", "report_no", "reviewer_name", "review_opinion"};
    }
}
