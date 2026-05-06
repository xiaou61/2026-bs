package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.CourseEnrollment;
import com.learningpath.mapper.CourseEnrollmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseEnrollmentService extends BaseCrudService<CourseEnrollment> {
    private final CourseEnrollmentMapper purchaseApprovalMapper;

    @Override
    protected BaseMapper<CourseEnrollment> mapper() {
        return purchaseApprovalMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"approval_no", "request_no", "approver_name", "approval_opinion"};
    }
}



