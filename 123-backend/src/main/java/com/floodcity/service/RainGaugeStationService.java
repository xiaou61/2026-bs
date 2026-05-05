package com.floodcity.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.floodcity.entity.RainGaugeStation;
import com.floodcity.mapper.RainGaugeStationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RainGaugeStationService extends BaseCrudService<RainGaugeStation> {
    private final RainGaugeStationMapper mapper;

    @Override
    protected BaseMapper<RainGaugeStation> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"station_no", "station_name", "district_name", "device_type"};
    }
}
