package com.carbonmanage.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carbonmanage.entity.CarbonWarning;
import com.carbonmanage.mapper.CarbonWarningMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarbonWarningService extends BaseCrudService<CarbonWarning> {
    private final CarbonWarningMapper carbonWarningMapper;

    @Override
    protected BaseMapper<CarbonWarning> mapper() {
        return carbonWarningMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"warning_no", "company_no", "warning_level", "trigger_reason"};
    }
}
