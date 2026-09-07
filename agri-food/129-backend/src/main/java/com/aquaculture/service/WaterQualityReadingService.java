package com.aquaculture.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aquaculture.entity.WaterQualityReading;
import com.aquaculture.mapper.WaterQualityReadingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaterQualityReadingService extends BaseCrudService<WaterQualityReading> {
    private final WaterQualityReadingMapper waterQualityReadingMapper;

    @Override
    protected BaseMapper<WaterQualityReading> mapper() {
        return waterQualityReadingMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"reading_no", "pond_no", "collect_time"};
    }
}
