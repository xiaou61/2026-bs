package com.droneinspect.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.droneinspect.entity.RiskWarning;
import com.droneinspect.mapper.RiskWarningMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiskWarningService extends BaseCrudService<RiskWarning> {
    private final RiskWarningMapper mapper;

    @Override
    protected BaseMapper<RiskWarning> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"warning_no", "risk_object", "warning_level", "warning_reason"};
    }
}
