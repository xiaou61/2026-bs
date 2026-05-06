package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.FollowUpPlan;
import com.psychologycare.mapper.FollowUpPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowUpPlanService extends BaseCrudService<FollowUpPlan> {
    private final FollowUpPlanMapper followUpPlanMapper;

    @Override
    protected BaseMapper<FollowUpPlan> mapper() {
        return followUpPlanMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"rule_no", "category_name", "min_stock", "warning_level"};
    }
}







