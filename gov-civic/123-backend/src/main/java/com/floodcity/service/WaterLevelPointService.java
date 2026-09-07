package com.floodcity.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.floodcity.entity.WaterLevelPoint;
import com.floodcity.mapper.WaterLevelPointMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaterLevelPointService extends BaseCrudService<WaterLevelPoint> {
    private final WaterLevelPointMapper mapper;

    @Override
    protected BaseMapper<WaterLevelPoint> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"point_no", "point_name", "district_name", "location_name"};
    }
}
