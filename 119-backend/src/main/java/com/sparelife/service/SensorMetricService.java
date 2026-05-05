package com.sparelife.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sparelife.entity.SensorMetric;
import com.sparelife.mapper.SensorMetricMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorMetricService extends BaseCrudService<SensorMetric> {
    private final SensorMetricMapper mapper;

    @Override
    protected BaseMapper<SensorMetric> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"metric_no", "equipment_name", "metric_source", "status"};
    }
}
