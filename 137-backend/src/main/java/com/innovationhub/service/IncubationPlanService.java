package com.innovationhub.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.innovationhub.entity.IncubationPlan;
import com.innovationhub.mapper.IncubationPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncubationPlanService extends BaseCrudService<IncubationPlan> {
    private final IncubationPlanMapper purchaseRequestMapper;

    @Override
    protected BaseMapper<IncubationPlan> mapper() {
        return purchaseRequestMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"request_no", "consumable_name", "request_qty", "applicant_name"};
    }
}


