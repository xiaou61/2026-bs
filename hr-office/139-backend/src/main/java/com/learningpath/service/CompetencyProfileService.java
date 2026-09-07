package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.CompetencyProfile;
import com.learningpath.mapper.CompetencyProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetencyProfileService extends BaseCrudService<CompetencyProfile> {
    private final CompetencyProfileMapper competencyProfileMapper;

    @Override
    protected BaseMapper<CompetencyProfile> mapper() {
        return competencyProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"profile_no", "learner_name", "competency_level", "evaluator_name"};
    }
}



