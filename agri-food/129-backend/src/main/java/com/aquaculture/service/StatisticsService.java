package com.aquaculture.service;

import com.aquaculture.mapper.PondProfileMapper;
import com.aquaculture.mapper.SensorDeviceMapper;
import com.aquaculture.mapper.WaterQualityReadingMapper;
import com.aquaculture.mapper.FeedingPlanMapper;
import com.aquaculture.mapper.FeedingRecordMapper;
import com.aquaculture.mapper.FishBatchMapper;
import com.aquaculture.mapper.GrowthSamplingMapper;
import com.aquaculture.mapper.DiseaseWarningMapper;
import com.aquaculture.mapper.MedicationRecordMapper;
import com.aquaculture.mapper.EquipmentDeviceMapper;
import com.aquaculture.mapper.WaterAlertRuleMapper;
import com.aquaculture.mapper.ProductionStatisticMapper;
import com.aquaculture.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final PondProfileMapper pondProfileMapper;
    private final SensorDeviceMapper sensorDeviceMapper;
    private final WaterQualityReadingMapper waterQualityReadingMapper;
    private final FeedingPlanMapper feedingPlanMapper;
    private final FeedingRecordMapper feedingRecordMapper;
    private final FishBatchMapper fishBatchMapper;
    private final GrowthSamplingMapper growthSamplingMapper;
    private final DiseaseWarningMapper diseaseWarningMapper;
    private final MedicationRecordMapper medicationRecordMapper;
    private final EquipmentDeviceMapper equipmentDeviceMapper;
    private final WaterAlertRuleMapper waterAlertRuleMapper;
    private final ProductionStatisticMapper productionStatisticMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("pondCount", pondProfileMapper.selectCount(null));
        data.put("readingCount", waterQualityReadingMapper.selectCount(null));
        data.put("feedingCount", feedingRecordMapper.selectCount(null));
        data.put("warningCount", diseaseWarningMapper.selectCount(null));
        data.put("waterTrend", Arrays.asList(16, 25, 33, 41, 52, 63, 75));
        data.put("pondPie", Arrays.asList(map("草鱼池", 35), map("鲈鱼池", 30), map("虾蟹池", 35)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
