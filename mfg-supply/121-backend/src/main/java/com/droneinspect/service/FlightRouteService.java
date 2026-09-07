package com.droneinspect.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.droneinspect.entity.FlightRoute;
import com.droneinspect.mapper.FlightRouteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightRouteService extends BaseCrudService<FlightRoute> {
    private final FlightRouteMapper mapper;

    @Override
    protected BaseMapper<FlightRoute> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"route_no", "route_name", "zone_name", "risk_level"};
    }
}
