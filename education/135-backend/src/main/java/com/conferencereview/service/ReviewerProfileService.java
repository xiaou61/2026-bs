package com.conferencereview.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.conferencereview.entity.ReviewerProfile;
import com.conferencereview.mapper.ReviewerProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewerProfileService extends BaseCrudService<ReviewerProfile> {
    private final ReviewerProfileMapper purchaseRequestMapper;

    @Override
    protected BaseMapper<ReviewerProfile> mapper() {
        return purchaseRequestMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"request_no", "consumable_name", "request_qty", "applicant_name"};
    }
}

