package com.conferencereview.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.conferencereview.entity.AgendaSchedule;
import com.conferencereview.mapper.AgendaScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgendaScheduleService extends BaseCrudService<AgendaSchedule> {
    private final AgendaScheduleMapper warningRuleMapper;

    @Override
    protected BaseMapper<AgendaSchedule> mapper() {
        return warningRuleMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"rule_no", "category_name", "min_stock", "warning_level"};
    }
}

