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
        return new String[]{"inspection_no", "equipment_name", "inspection_theme", "inspection_content"};
    }
}








