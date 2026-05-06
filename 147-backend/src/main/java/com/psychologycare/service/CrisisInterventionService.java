package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.CrisisIntervention;
import com.psychologycare.mapper.CrisisInterventionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrisisInterventionService extends BaseCrudService<CrisisIntervention> {
    private final CrisisInterventionMapper outboundRecordMapper;

    @Override
    protected BaseMapper<CrisisIntervention> mapper() {
        return outboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"outbound_no", "consumable_name", "lab_name", "outbound_qty"};
    }
}







