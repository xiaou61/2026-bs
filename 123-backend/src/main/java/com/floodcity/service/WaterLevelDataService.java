package com.floodcity.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.floodcity.entity.WaterLevelData;
import com.floodcity.mapper.WaterLevelDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaterLevelDataService extends BaseCrudService<WaterLevelData> {
    private final WaterLevelDataMapper mapper;

    @Override
    protected BaseMapper<WaterLevelData> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"data_no", "point_name", "collect_time", "source_type"};
    }
}
