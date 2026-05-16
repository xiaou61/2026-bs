package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.ExamScore;
import com.learningpath.mapper.ExamScoreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamScoreService extends BaseCrudService<ExamScore> {
    private final ExamScoreMapper examScoreMapper;

    @Override
    protected BaseMapper<ExamScore> mapper() {
        return examScoreMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"score_no", "exam_name", "learner_name", "evaluator_name"};
    }
}



