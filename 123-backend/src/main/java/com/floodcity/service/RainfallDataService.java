package com.floodcity.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.floodcity.entity.RainfallData;
import com.floodcity.mapper.RainfallDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RainfallDataService extends BaseCrudService<RainfallData> {
    private final RainfallDataMapper mapper;

    @Override
    protected BaseMapper<RainfallData> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"data_no", "station_name", "collect_time", "rain_level"};
    }
}
