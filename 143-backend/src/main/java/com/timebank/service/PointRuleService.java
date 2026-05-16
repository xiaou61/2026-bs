package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.PointRule;
import com.timebank.mapper.PointRuleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointRuleService extends BaseCrudService<PointRule> {
    private final PointRuleMapper pointRuleMapper;

    @Override
    protected BaseMapper<PointRule> mapper() {
        return pointRuleMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"rule_no", "project_name", "point_item", "effective_time"};
    }
}

