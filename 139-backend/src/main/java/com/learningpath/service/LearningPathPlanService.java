package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.LearningPathPlan;
import com.learningpath.mapper.LearningPathPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LearningPathPlanService extends BaseCrudService<LearningPathPlan> {
    private final LearningPathPlanMapper stockItemMapper;

    @Override
    protected BaseMapper<LearningPathPlan> mapper() {
        return stockItemMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"stock_no", "consumable_name", "lab_name", "current_qty"};
    }
}



