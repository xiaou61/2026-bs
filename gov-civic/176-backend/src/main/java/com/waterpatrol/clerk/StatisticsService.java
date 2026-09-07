package com.waterpatrol.clerk;

import com.waterpatrol.mapper.WaterStationMapper;
import com.waterpatrol.mapper.PipelineSectionMapper;
import com.waterpatrol.mapper.PatrolRouteMapper;
import com.waterpatrol.mapper.ValveLedgerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final WaterStationMapper waterStationMapper;
    private final PipelineSectionMapper pipelineSectionMapper;
    private final PatrolRouteMapper patrolRouteMapper;
    private final ValveLedgerMapper valveLedgerMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("stationCount", waterStationMapper.countAll());
        data.put("sectionCount", pipelineSectionMapper.countAll());
        data.put("routeCount", patrolRouteMapper.countAll());
        data.put("valveCount", valveLedgerMapper.countAll());
        data.put("trend", Arrays.asList(58, 74, 88, 105, 121, 137, 154));
        data.put("pie", Arrays.asList(map("待巡线", 31), map("巡线中", 44), map("检修中", 19), map("已完成", 68)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
