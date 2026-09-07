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
        return new String[]{"follow_up_no", "case_theme", "follow_up_stage", "follow_up_content"};
    }
}







