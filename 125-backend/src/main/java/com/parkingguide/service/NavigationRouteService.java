package com.parkingguide.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parkingguide.entity.NavigationRoute;
import com.parkingguide.mapper.NavigationRouteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NavigationRouteService extends BaseCrudService<NavigationRoute> {
    private final NavigationRouteMapper mapper;

    @Override
    protected BaseMapper<NavigationRoute> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"route_no", "lot_name", "entry_name", "target_area"};
    }
}
