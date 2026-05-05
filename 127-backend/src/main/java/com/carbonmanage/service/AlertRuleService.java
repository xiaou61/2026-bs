package com.carbonmanage.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carbonmanage.entity.AlertRule;
import com.carbonmanage.mapper.AlertRuleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertRuleService extends BaseCrudService<AlertRule> {
    private final AlertRuleMapper alertRuleMapper;

    @Override
    protected BaseMapper<AlertRule> mapper() {
        return alertRuleMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"rule_no", "rule_name", "metric_name", "alert_level"};
    }
}
