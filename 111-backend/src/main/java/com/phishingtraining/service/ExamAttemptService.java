package com.phishingtraining.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phishingtraining.entity.ExamAttempt;
import com.phishingtraining.mapper.ExamAttemptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamAttemptService extends BaseCrudService<ExamAttempt> {
    private final ExamAttemptMapper mapper;

    @Override
    protected BaseMapper<ExamAttempt> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"attempt_no", "employee_name", "exam_name", "result_status"};
    }

}
