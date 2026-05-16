package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.CrisisIntervention;
import com.psychologycare.mapper.CrisisInterventionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrisisInterventionService extends BaseCrudService<CrisisIntervention> {
    private final CrisisInterventionMapper crisisInterventionMapper;

    @Override
    protected BaseMapper<CrisisIntervention> mapper() {
        return crisisInterventionMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"intervention_no", "case_theme", "intervention_type", "target_person"};
    }
}







