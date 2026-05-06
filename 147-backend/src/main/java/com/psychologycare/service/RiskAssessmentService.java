package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.RiskAssessment;
import com.psychologycare.mapper.RiskAssessmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiskAssessmentService extends BaseCrudService<RiskAssessment> {
    private final RiskAssessmentMapper inboundRecordMapper;

    @Override
    protected BaseMapper<RiskAssessment> mapper() {
        return inboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"inbound_no", "order_no", "consumable_name", "inbound_qty"};
    }
}







