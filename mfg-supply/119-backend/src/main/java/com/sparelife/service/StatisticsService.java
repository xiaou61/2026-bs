package com.sparelife.service;

import com.sparelife.mapper.EquipmentAssetMapper;
import com.sparelife.mapper.SparePartCatalogMapper;
import com.sparelife.mapper.SparePartStockMapper;
import com.sparelife.mapper.SparePartInboundMapper;
import com.sparelife.mapper.SparePartOutboundMapper;
import com.sparelife.mapper.UsageRecordMapper;
import com.sparelife.mapper.SensorMetricMapper;
import com.sparelife.mapper.FailureRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final EquipmentAssetMapper equipmentAssetMapper;
    private final SparePartCatalogMapper sparePartCatalogMapper;
    private final SparePartStockMapper sparePartStockMapper;
    private final SparePartInboundMapper sparePartInboundMapper;
    private final SparePartOutboundMapper sparePartOutboundMapper;
    private final UsageRecordMapper usageRecordMapper;
    private final SensorMetricMapper sensorMetricMapper;
    private final FailureRecordMapper failureRecordMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("assetCount", equipmentAssetMapper.selectCount(null));
        data.put("stockCount", sparePartCatalogMapper.selectCount(null));
        data.put("predictionCount", sparePartStockMapper.selectCount(null));
        data.put("warningCount", sparePartInboundMapper.selectCount(null));
        data.put("riskTrend", Arrays.asList(12, 19, 26, 33, 41, 52, 68));
        data.put("partPie", Arrays.asList(map("核心设备", 36), map("巡检维保", 28), map("风险预警", 22), map("库存计划", 14)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
