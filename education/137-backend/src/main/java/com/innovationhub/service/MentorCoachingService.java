package com.innovationhub.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.innovationhub.entity.MentorCoaching;
import com.innovationhub.mapper.MentorCoachingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentorCoachingService extends BaseCrudService<MentorCoaching> {
    private final MentorCoachingMapper purchaseApprovalMapper;

    @Override
    protected BaseMapper<MentorCoaching> mapper() {
        return purchaseApprovalMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"approval_no", "request_no", "approver_name", "approval_opinion"};
    }
}


