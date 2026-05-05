package com.droneinspect.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.droneinspect.entity.InspectionZone;
import com.droneinspect.mapper.InspectionZoneMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InspectionZoneService extends BaseCrudService<InspectionZone> {
    private final InspectionZoneMapper mapper;

    @Override
    protected BaseMapper<InspectionZone> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"zone_no", "zone_name", "city_name", "risk_level"};
    }
}
