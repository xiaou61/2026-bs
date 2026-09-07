package com.phishingtraining.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phishingtraining.entity.TrainingExam;
import com.phishingtraining.mapper.TrainingExamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingExamService extends BaseCrudService<TrainingExam> {
    private final TrainingExamMapper mapper;

    @Override
    protected BaseMapper<TrainingExam> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"exam_name", "exam_code", "course_name", "owner_name"};
    }

}
