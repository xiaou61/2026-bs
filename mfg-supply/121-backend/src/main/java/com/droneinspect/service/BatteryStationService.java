package com.droneinspect.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.droneinspect.entity.BatteryStation;
import com.droneinspect.mapper.BatteryStationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatteryStationService extends BaseCrudService<BatteryStation> {
    private final BatteryStationMapper mapper;

    @Override
    protected BaseMapper<BatteryStation> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"station_no", "station_name", "zone_name", "manager_name"};
    }
}
