package com.aquaculture.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aquaculture.entity.FeedingPlan;
import com.aquaculture.mapper.FeedingPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedingPlanService extends BaseCrudService<FeedingPlan> {
    private final FeedingPlanMapper feedingPlanMapper;

    @Override
    protected BaseMapper<FeedingPlan> mapper() {
        return feedingPlanMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"plan_no", "pond_no", "fish_species", "feed_type"};
    }
}
