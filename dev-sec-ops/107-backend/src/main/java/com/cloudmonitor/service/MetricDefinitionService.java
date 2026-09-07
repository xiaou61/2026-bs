package com.cloudmonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudmonitor.entity.MetricDefinition;
import com.cloudmonitor.mapper.MetricDefinitionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetricDefinitionService extends BaseCrudService<MetricDefinition> {
    private final MetricDefinitionMapper mapper;

    @Override
    protected BaseMapper<MetricDefinition> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"metric_name", "metric_code", "unit_name", "description"};
    }


}
