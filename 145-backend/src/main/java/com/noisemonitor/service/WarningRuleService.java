package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.WarningRule;
import com.noisemonitor.mapper.WarningRuleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarningRuleService extends BaseCrudService<WarningRule> {
    private final WarningRuleMapper warningRuleMapper;

    @Override
    protected BaseMapper<WarningRule> mapper() {
        return warningRuleMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"rule_no", "complaint_title", "warning_metric", "threshold_config"};
    }
}






