package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.PenaltyDecision;
import com.noisemonitor.mapper.PenaltyDecisionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PenaltyDecisionService extends BaseCrudService<PenaltyDecision> {
    private final PenaltyDecisionMapper outboundRecordMapper;

    @Override
    protected BaseMapper<PenaltyDecision> mapper() {
        return outboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"outbound_no", "consumable_name", "lab_name", "outbound_qty"};
    }
}






