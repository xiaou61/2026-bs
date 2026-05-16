package com.farmmachinery.clerk;

import com.farmmachinery.mapper.ServiceStationMapper;
import com.farmmachinery.mapper.MachineAssetMapper;
import com.farmmachinery.mapper.FarmerProfileMapper;
import com.farmmachinery.mapper.DriverProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ServiceStationMapper serviceStationMapper;
    private final MachineAssetMapper machineAssetMapper;
    private final FarmerProfileMapper farmerProfileMapper;
    private final DriverProfileMapper driverProfileMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("stationCount", serviceStationMapper.countAll());
        data.put("machineCount", machineAssetMapper.countAll());
        data.put("farmerCount", farmerProfileMapper.countAll());
        data.put("driverCount", driverProfileMapper.countAll());
        data.put("trend", Arrays.asList(58, 74, 88, 105, 121, 137, 154));
        data.put("pie", Arrays.asList(map("已预约", 31), map("已调度", 44), map("作业中", 19), map("维修中", 12), map("已闭环", 68)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
