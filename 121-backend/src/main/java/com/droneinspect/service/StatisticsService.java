package com.droneinspect.service;

import com.droneinspect.mapper.DroneDeviceMapper;
import com.droneinspect.mapper.InspectionTaskMapper;
import com.droneinspect.mapper.DefectReportMapper;
import com.droneinspect.mapper.RectificationOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final DroneDeviceMapper droneDeviceMapper;
    private final InspectionTaskMapper inspectionTaskMapper;
    private final DefectReportMapper defectReportMapper;
    private final RectificationOrderMapper rectificationOrderMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("droneCount", droneDeviceMapper.selectCount(null));
        data.put("taskCount", inspectionTaskMapper.selectCount(null));
        data.put("defectCount", defectReportMapper.selectCount(null));
        data.put("rectifyCount", rectificationOrderMapper.selectCount(null));
        data.put("flightTrend", Arrays.asList(12, 19, 26, 33, 41, 52, 68));
        data.put("defectPie", Arrays.asList(map("一般缺陷", 36), map("严重缺陷", 28), map("紧急缺陷", 22), map("已闭环", 14)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
