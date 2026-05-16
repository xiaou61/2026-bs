package com.schoolbus.clerk;

import com.schoolbus.mapper.BusRouteMapper;
import com.schoolbus.mapper.BusStopMapper;
import com.schoolbus.mapper.VehicleProfileMapper;
import com.schoolbus.mapper.DriverProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final BusRouteMapper busRouteMapper;
    private final BusStopMapper busStopMapper;
    private final VehicleProfileMapper vehicleProfileMapper;
    private final DriverProfileMapper driverProfileMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("routeCount", busRouteMapper.countAll());
        data.put("stopCount", busStopMapper.countAll());
        data.put("vehicleCount", vehicleProfileMapper.countAll());
        data.put("driverCount", driverProfileMapper.countAll());
        data.put("trend", Arrays.asList(118, 142, 169, 201, 236, 258, 291));
        data.put("pie", Arrays.asList(map("已预约", 64), map("已上车", 31), map("已下车", 42), map("异常待处置", 17)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
