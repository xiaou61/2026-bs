package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.PointRule;
import com.timebank.mapper.PointRuleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointRuleService extends BaseCrudService<PointRule> {
    private final PointRuleMapper warningRuleMapper;

    @Override
    protected BaseMapper<PointRule> mapper() {
        return warningRuleMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"rule_no", "category_name", "min_stock", "warning_level"};
    }
}





