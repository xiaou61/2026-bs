package com.aquaculture.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aquaculture.entity.WaterAlertRule;
import com.aquaculture.mapper.WaterAlertRuleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaterAlertRuleService extends BaseCrudService<WaterAlertRule> {
    private final WaterAlertRuleMapper waterAlertRuleMapper;

    @Override
    protected BaseMapper<WaterAlertRule> mapper() {
        return waterAlertRuleMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"rule_no", "rule_name", "metric_name", "alert_level"};
    }
}
