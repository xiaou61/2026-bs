package com.innovationhub.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.innovationhub.entity.AchievementShowcase;
import com.innovationhub.mapper.AchievementShowcaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementShowcaseService extends BaseCrudService<AchievementShowcase> {
    private final AchievementShowcaseMapper warningRuleMapper;

    @Override
    protected BaseMapper<AchievementShowcase> mapper() {
        return warningRuleMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"rule_no", "category_name", "min_stock", "warning_level"};
    }
}


