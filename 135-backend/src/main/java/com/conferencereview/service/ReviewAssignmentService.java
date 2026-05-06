package com.conferencereview.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.conferencereview.entity.ReviewAssignment;
import com.conferencereview.mapper.ReviewAssignmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewAssignmentService extends BaseCrudService<ReviewAssignment> {
    private final ReviewAssignmentMapper purchaseApprovalMapper;

    @Override
    protected BaseMapper<ReviewAssignment> mapper() {
        return purchaseApprovalMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"approval_no", "request_no", "approver_name", "approval_opinion"};
    }
}

