package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.AssessmentExam;
import com.learningpath.mapper.AssessmentExamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssessmentExamService extends BaseCrudService<AssessmentExam> {
    private final AssessmentExamMapper assessmentExamMapper;

    @Override
    protected BaseMapper<AssessmentExam> mapper() {
        return assessmentExamMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"exam_no", "exam_name", "course_name"};
    }
}



