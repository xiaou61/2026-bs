package com.drugreaction.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drugreaction.entity.RiskAssessment;
import com.drugreaction.mapper.RiskAssessmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiskAssessmentService extends BaseCrudService<RiskAssessment> {
    private final RiskAssessmentMapper riskAssessmentMapper;

    @Override
    protected BaseMapper<RiskAssessment> mapper() {
        return riskAssessmentMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"assess_no", "report_no", "assess_level", "assessor_name"};
    }
}
