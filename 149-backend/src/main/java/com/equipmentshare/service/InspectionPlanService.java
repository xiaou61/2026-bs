package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.InspectionPlan;
import com.equipmentshare.mapper.InspectionPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InspectionPlanService extends BaseCrudService<InspectionPlan> {
    private final InspectionPlanMapper inspectionPlanMapper;

    @Override
    protected BaseMapper<InspectionPlan> mapper() {
        return inspectionPlanMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"rule_no", "category_name", "min_stock", "warning_level"};
    }
}








