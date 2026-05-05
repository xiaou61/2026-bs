package com.floodcity.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.floodcity.entity.FloodWarning;
import com.floodcity.mapper.FloodWarningMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FloodWarningService extends BaseCrudService<FloodWarning> {
    private final FloodWarningMapper mapper;

    @Override
    protected BaseMapper<FloodWarning> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"warning_no", "point_name", "warning_level", "trigger_reason"};
    }
}
