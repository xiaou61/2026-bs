package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.LearningPathPlan;
import com.learningpath.mapper.LearningPathPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LearningPathPlanService extends BaseCrudService<LearningPathPlan> {
    private final LearningPathPlanMapper learningPathPlanMapper;

    @Override
    protected BaseMapper<LearningPathPlan> mapper() {
        return learningPathPlanMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"path_no", "learner_name", "path_name"};
    }
}



