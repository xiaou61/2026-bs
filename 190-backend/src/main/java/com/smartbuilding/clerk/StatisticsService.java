package com.smartbuilding.clerk;

import com.smartbuilding.mapper.BuildingProfileMapper;
import com.smartbuilding.mapper.EquipmentAssetMapper;
import com.smartbuilding.mapper.TenantProfileMapper;
import com.smartbuilding.mapper.VisitRepairTicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final BuildingProfileMapper buildingProfileMapper;
    private final EquipmentAssetMapper equipmentAssetMapper;
    private final TenantProfileMapper tenantProfileMapper;
    private final VisitRepairTicketMapper visitRepairTicketMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("buildingCount", buildingProfileMapper.countAll());
        data.put("equipmentCount", equipmentAssetMapper.countAll());
        data.put("tenantCount", tenantProfileMapper.countAll());
        data.put("ticketCount", visitRepairTicketMapper.countAll());
        data.put("trend", Arrays.asList(58, 74, 88, 105, 121, 137, 154));
        data.put("pie", Arrays.asList(map("已报修", 31), map("已派工", 44), map("处理中", 19), map("预警中", 12), map("已闭环", 68)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
