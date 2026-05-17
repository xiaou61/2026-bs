package com.housekeeping.clerk;

import com.housekeeping.mapper.ServiceStationMapper;
import com.housekeeping.mapper.ResidentProfileMapper;
import com.housekeeping.mapper.WorkerProfileMapper;
import com.housekeeping.mapper.ServiceCatalogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ServiceStationMapper serviceStationMapper;
    private final ResidentProfileMapper residentProfileMapper;
    private final WorkerProfileMapper workerProfileMapper;
    private final ServiceCatalogMapper serviceCatalogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("stationCount", serviceStationMapper.countAll());
        data.put("residentCount", residentProfileMapper.countAll());
        data.put("workerCount", workerProfileMapper.countAll());
        data.put("serviceCount", serviceCatalogMapper.countAll());
        data.put("trend", Arrays.asList(31, 46, 58, 72, 86, 101, 119));
        data.put("pie", Arrays.asList(map("已预约", 36), map("待派单", 24), map("服务中", 32), map("已评价", 28), map("投诉中", 12)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
