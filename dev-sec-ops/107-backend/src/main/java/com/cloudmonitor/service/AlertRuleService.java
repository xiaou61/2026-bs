package com.cloudmonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudmonitor.entity.AlertRule;
import com.cloudmonitor.mapper.AlertRuleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertRuleService extends BaseCrudService<AlertRule> {
    private final AlertRuleMapper mapper;

    @Override
    protected BaseMapper<AlertRule> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"rule_name", "metric_code", "operator_type", "threshold_value"};
    }


}
