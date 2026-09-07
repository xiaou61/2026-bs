package com.coldchain.service;

import com.coldchain.mapper.TransportOrderMapper;
import com.coldchain.mapper.TemperatureRecordMapper;
import com.coldchain.mapper.ExceptionAlertMapper;
import com.coldchain.mapper.DisposalTaskMapper;
import com.coldchain.mapper.ColdDeviceMapper;
import com.coldchain.mapper.GpsTrackMapper;
import com.coldchain.mapper.ResponsibilityTraceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final TransportOrderMapper orderMapper;
    private final TemperatureRecordMapper temperatureMapper;
    private final ExceptionAlertMapper alertMapper;
    private final DisposalTaskMapper taskMapper;
    private final ColdDeviceMapper deviceMapper;
    private final GpsTrackMapper trackMapper;
    private final ResponsibilityTraceMapper responsibilityMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("orderCount", orderMapper.countAll());
        data.put("temperatureCount", temperatureMapper.countAll());
        data.put("alertCount", alertMapper.countAll());
        data.put("taskCount", taskMapper.countAll());
        data.put("deviceCount", deviceMapper.countAll());
        data.put("trackCount", trackMapper.countAll());
        data.put("responsibilityCount", responsibilityMapper.countAll());
        data.put("temperatureTrend", Arrays.asList(42, 56, 64, 70, 88, 92, 106));
        data.put("alertPie", Arrays.asList(map("温度异常", 42), map("设备离线", 26), map("路线偏离", 18), map("湿度异常", 14)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
