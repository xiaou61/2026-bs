package com.hazardwaste.clerk;

import com.hazardwaste.mapper.ParkEnterpriseMapper;
import com.hazardwaste.mapper.WasteCategoryMapper;
import com.hazardwaste.mapper.StorageFacilityMapper;
import com.hazardwaste.mapper.StorageInboundMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ParkEnterpriseMapper parkEnterpriseMapper;
    private final WasteCategoryMapper wasteCategoryMapper;
    private final StorageFacilityMapper storageFacilityMapper;
    private final StorageInboundMapper storageInboundMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("enterpriseCount", parkEnterpriseMapper.countAll());
        data.put("wasteCount", wasteCategoryMapper.countAll());
        data.put("facilityCount", storageFacilityMapper.countAll());
        data.put("inboundCount", storageInboundMapper.countAll());
        data.put("trend", Arrays.asList(31, 46, 58, 72, 86, 101, 119));
        data.put("pie", Arrays.asList(map("已入库", 35), map("巡检中", 22), map("待转运", 29), map("转运中", 18), map("已交接", 31)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
