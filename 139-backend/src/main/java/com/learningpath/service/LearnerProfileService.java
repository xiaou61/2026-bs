package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.LearnerProfile;
import com.learningpath.mapper.LearnerProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LearnerProfileService extends BaseCrudService<LearnerProfile> {
    private final LearnerProfileMapper learnerProfileMapper;

    @Override
    protected BaseMapper<LearnerProfile> mapper() {
        return learnerProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"learner_no", "learner_name", "department_name", "position_name"};
    }
}



