package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.RiskAssessment;
import com.psychologycare.mapper.RiskAssessmentMapper;
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
        return new String[]{"assessment_no", "case_theme", "assessor_name", "risk_level"};
    }
}







