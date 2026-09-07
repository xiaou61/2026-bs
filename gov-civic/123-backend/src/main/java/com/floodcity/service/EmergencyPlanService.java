package com.floodcity.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.floodcity.entity.EmergencyPlan;
import com.floodcity.mapper.EmergencyPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmergencyPlanService extends BaseCrudService<EmergencyPlan> {
    private final EmergencyPlanMapper mapper;

    @Override
    protected BaseMapper<EmergencyPlan> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"plan_no", "plan_name", "apply_level", "department_name"};
    }
}
