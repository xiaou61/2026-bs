package com.floodcity.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.floodcity.entity.WarningRule;
import com.floodcity.mapper.WarningRuleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarningRuleService extends BaseCrudService<WarningRule> {
    private final WarningRuleMapper mapper;

    @Override
    protected BaseMapper<WarningRule> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"rule_no", "rule_name", "metric_type", "warning_level"};
    }
}
