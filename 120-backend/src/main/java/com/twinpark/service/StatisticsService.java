package com.twinpark.service;

import com.twinpark.mapper.ParkBuildingMapper;
import com.twinpark.mapper.TwinDeviceMapper;
import com.twinpark.mapper.InspectionRouteMapper;
import com.twinpark.mapper.InspectionPointMapper;
import com.twinpark.mapper.InspectionTaskMapper;
import com.twinpark.mapper.InspectionRecordMapper;
import com.twinpark.mapper.DefectReportMapper;
import com.twinpark.mapper.WorkOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ParkBuildingMapper parkBuildingMapper;
    private final TwinDeviceMapper twinDeviceMapper;
    private final InspectionRouteMapper inspectionRouteMapper;
    private final InspectionPointMapper inspectionPointMapper;
    private final InspectionTaskMapper inspectionTaskMapper;
    private final InspectionRecordMapper inspectionRecordMapper;
    private final DefectReportMapper defectReportMapper;
    private final WorkOrderMapper workOrderMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("deviceCount", parkBuildingMapper.countAll());
        data.put("taskCount", twinDeviceMapper.countAll());
        data.put("defectCount", inspectionRouteMapper.countAll());
        data.put("workOrderCount", inspectionPointMapper.countAll());
        data.put("inspectionTrend", Arrays.asList(12, 19, 26, 33, 41, 52, 68));
        data.put("devicePie", Arrays.asList(map("核心设备", 36), map("巡检维保", 28), map("风险预警", 22), map("库存计划", 14)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
