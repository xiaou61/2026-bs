package com.dormenergy.service;

import com.dormenergy.mapper.DormBuildingMapper;
import com.dormenergy.mapper.DormRoomMapper;
import com.dormenergy.mapper.MeterDeviceMapper;
import com.dormenergy.mapper.EnergyReadingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final DormBuildingMapper dormBuildingMapper;
    private final DormRoomMapper dormRoomMapper;
    private final MeterDeviceMapper meterDeviceMapper;
    private final EnergyReadingMapper energyReadingMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("buildingCount", dormBuildingMapper.countAll());
        data.put("roomCount", dormRoomMapper.countAll());
        data.put("meterCount", meterDeviceMapper.countAll());
        data.put("readingCount", energyReadingMapper.countAll());
        data.put("trend", Arrays.asList(120, 146, 163, 188, 201, 234, 260));
        data.put("pie", Arrays.asList(map("读数待复核", 32), map("能耗正常", 58), map("异常预警", 25), map("排行已发布", 8)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
