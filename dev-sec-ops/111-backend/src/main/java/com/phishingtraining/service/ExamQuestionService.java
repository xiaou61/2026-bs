package com.phishingtraining.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phishingtraining.entity.ExamQuestion;
import com.phishingtraining.mapper.ExamQuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamQuestionService extends BaseCrudService<ExamQuestion> {
    private final ExamQuestionMapper mapper;

    @Override
    protected BaseMapper<ExamQuestion> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"question_title", "exam_name", "question_type", "answer_key"};
    }

}
