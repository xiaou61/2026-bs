package com.datamasking.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datamasking.entity.SensitiveRule;
import com.datamasking.mapper.SensitiveRuleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensitiveRuleService extends BaseCrudService<SensitiveRule> {
    private final SensitiveRuleMapper mapper;

    @Override
    protected BaseMapper<SensitiveRule> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"rule_name", "rule_type", "risk_level", "masking_type"};
    }

}
