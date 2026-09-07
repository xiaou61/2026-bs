package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.PenaltyDecision;
import com.noisemonitor.mapper.PenaltyDecisionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PenaltyDecisionService extends BaseCrudService<PenaltyDecision> {
    private final PenaltyDecisionMapper penaltyDecisionMapper;

    @Override
    protected BaseMapper<PenaltyDecision> mapper() {
        return penaltyDecisionMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"penalty_no", "complaint_title", "penalty_type", "penalty_target"};
    }
}






