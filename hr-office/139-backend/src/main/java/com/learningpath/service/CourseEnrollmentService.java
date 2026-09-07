package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.CourseEnrollment;
import com.learningpath.mapper.CourseEnrollmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseEnrollmentService extends BaseCrudService<CourseEnrollment> {
    private final CourseEnrollmentMapper courseEnrollmentMapper;

    @Override
    protected BaseMapper<CourseEnrollment> mapper() {
        return courseEnrollmentMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"enrollment_no", "course_name", "learner_name", "approver_name"};
    }
}



