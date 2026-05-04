package com.cloudmonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudmonitor.entity.MetricSample;
import com.cloudmonitor.mapper.MetricSampleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetricSampleService extends BaseCrudService<MetricSample> {
    private final MetricSampleMapper mapper;

    @Override
    protected BaseMapper<MetricSample> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"metric_code", "metric_value", "sample_time", "health_level"};
    }


}
