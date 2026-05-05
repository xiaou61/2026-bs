package com.greenhouse.service;

import com.greenhouse.mapper.GreenhouseProfileMapper;
import com.greenhouse.mapper.CropBatchMapper;
import com.greenhouse.mapper.EnvironmentSensorMapper;
import com.greenhouse.mapper.EnvironmentReadingMapper;
import com.greenhouse.mapper.IrrigationTaskMapper;
import com.greenhouse.mapper.FertilizerPlanMapper;
import com.greenhouse.mapper.PestWarningMapper;
import com.greenhouse.mapper.DiseaseDiagnosisMapper;
import com.greenhouse.mapper.ControlDeviceMapper;
import com.greenhouse.mapper.RemoteCommandMapper;
import com.greenhouse.mapper.HarvestRecordMapper;
import com.greenhouse.mapper.MaintenanceTicketMapper;
import com.greenhouse.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final GreenhouseProfileMapper greenhouseProfileMapper;
    private final CropBatchMapper cropBatchMapper;
    private final EnvironmentSensorMapper environmentSensorMapper;
    private final EnvironmentReadingMapper environmentReadingMapper;
    private final IrrigationTaskMapper irrigationTaskMapper;
    private final FertilizerPlanMapper fertilizerPlanMapper;
    private final PestWarningMapper pestWarningMapper;
    private final DiseaseDiagnosisMapper diseaseDiagnosisMapper;
    private final ControlDeviceMapper controlDeviceMapper;
    private final RemoteCommandMapper remoteCommandMapper;
    private final HarvestRecordMapper harvestRecordMapper;
    private final MaintenanceTicketMapper maintenanceTicketMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("greenhouseCount", greenhouseProfileMapper.countAll());
        data.put("sensorCount", environmentSensorMapper.countAll());
        data.put("warningCount", pestWarningMapper.countAll());
        data.put("commandCount", remoteCommandMapper.countAll());
        data.put("environmentTrend", Arrays.asList(16, 25, 33, 41, 52, 63, 75));
        data.put("cropPie", Arrays.asList(map("番茄", 36), map("黄瓜", 28), map("草莓", 36)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
