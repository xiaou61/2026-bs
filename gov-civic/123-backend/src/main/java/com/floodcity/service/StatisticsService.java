package com.floodcity.service;

import com.floodcity.mapper.WaterLevelPointMapper;
import com.floodcity.mapper.FloodWarningMapper;
import com.floodcity.mapper.DispatchTaskMapper;
import com.floodcity.mapper.MaterialReserveMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final WaterLevelPointMapper waterLevelPointMapper;
    private final FloodWarningMapper floodWarningMapper;
    private final DispatchTaskMapper dispatchTaskMapper;
    private final MaterialReserveMapper materialReserveMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("pointCount", waterLevelPointMapper.selectCount(null));
        data.put("warningCount", floodWarningMapper.selectCount(null));
        data.put("taskCount", dispatchTaskMapper.selectCount(null));
        data.put("materialCount", materialReserveMapper.selectCount(null));
        data.put("warningTrend", Arrays.asList(12, 19, 26, 33, 41, 52, 68));
        data.put("warningPie", Arrays.asList(map("蓝色预警", 36), map("黄色预警", 28), map("橙色预警", 22), map("红色预警", 14)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
