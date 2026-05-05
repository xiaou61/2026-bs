package com.sparelife.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sparelife.entity.RiskWarning;
import com.sparelife.mapper.RiskWarningMapper;
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
        return new String[]{"warning_no", "equipment_name", "part_name", "warning_level"};
    }
}
