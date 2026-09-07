package com.smartwarehouse.service;

import com.smartwarehouse.mapper.AgvTaskMapper;
import com.smartwarehouse.mapper.AgvVehicleMapper;
import com.smartwarehouse.mapper.DeviceMaintenanceMapper;
import com.smartwarehouse.mapper.ExceptionAlertMapper;
import com.smartwarehouse.mapper.InventoryItemMapper;
import com.smartwarehouse.mapper.LocationRecommendationMapper;
import com.smartwarehouse.mapper.StorageLocationMapper;
import com.smartwarehouse.mapper.WarehouseZoneMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final WarehouseZoneMapper zoneMapper;
    private final StorageLocationMapper locationMapper;
    private final AgvVehicleMapper vehicleMapper;
    private final InventoryItemMapper inventoryMapper;
    private final AgvTaskMapper taskMapper;
    private final LocationRecommendationMapper recommendationMapper;
    private final DeviceMaintenanceMapper maintenanceMapper;
    private final ExceptionAlertMapper alertMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("zoneCount", zoneMapper.countAll());
        data.put("locationCount", locationMapper.countAll());
        data.put("vehicleCount", vehicleMapper.countAll());
        data.put("inventoryCount", inventoryMapper.countAll());
        data.put("taskCount", taskMapper.countAll());
        data.put("recommendationCount", recommendationMapper.countAll());
        data.put("maintenanceCount", maintenanceMapper.countAll());
        data.put("alertCount", alertMapper.countAll());
        data.put("taskTrend", Arrays.asList(16, 22, 30, 38, 44, 51, 63));
        data.put("zonePie", Arrays.asList(map("立体库A区", 38), map("自动化库B区", 27), map("冷链C区", 20), map("重货D区", 15)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
