package com.cloudmonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudmonitor.entity.CapacityPlan;
import com.cloudmonitor.mapper.CapacityPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CapacityPlanService extends BaseCrudService<CapacityPlan> {
    private final CapacityPlanMapper mapper;

    @Override
    protected BaseMapper<CapacityPlan> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"plan_name", "cpu_usage", "memory_usage", "disk_usage"};
    }


}
