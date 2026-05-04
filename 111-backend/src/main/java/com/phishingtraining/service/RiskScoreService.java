package com.phishingtraining.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phishingtraining.entity.RiskScore;
import com.phishingtraining.mapper.RiskScoreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiskScoreService extends BaseCrudService<RiskScore> {
    private final RiskScoreMapper mapper;

    @Override
    protected BaseMapper<RiskScore> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"employee_name", "department_name", "risk_level", "reviewer_name"};
    }

}
