package com.drugreaction.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drugreaction.entity.FollowupPlan;
import com.drugreaction.mapper.FollowupPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowupPlanService extends BaseCrudService<FollowupPlan> {
    private final FollowupPlanMapper followupPlanMapper;

    @Override
    protected BaseMapper<FollowupPlan> mapper() {
        return followupPlanMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"plan_no", "report_no", "follow_date", "follow_method"};
    }
}
