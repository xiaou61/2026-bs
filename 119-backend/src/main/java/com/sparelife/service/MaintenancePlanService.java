package com.sparelife.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sparelife.entity.MaintenancePlan;
import com.sparelife.mapper.MaintenancePlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaintenancePlanService extends BaseCrudService<MaintenancePlan> {
    private final MaintenancePlanMapper mapper;

    @Override
    protected BaseMapper<MaintenancePlan> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"plan_no", "equipment_name", "part_name", "owner_name"};
    }
}
