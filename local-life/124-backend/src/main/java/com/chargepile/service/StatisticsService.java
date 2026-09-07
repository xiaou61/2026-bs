package com.chargepile.service;

import com.chargepile.mapper.ChargingStationMapper;
import com.chargepile.mapper.ChargingPileMapper;
import com.chargepile.mapper.AppointmentOrderMapper;
import com.chargepile.mapper.FaultReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ChargingStationMapper chargingStationMapper;
    private final ChargingPileMapper chargingPileMapper;
    private final AppointmentOrderMapper appointmentOrderMapper;
    private final FaultReportMapper faultReportMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("stationCount", chargingStationMapper.countAll());
        data.put("pileCount", chargingPileMapper.countAll());
        data.put("appointmentCount", appointmentOrderMapper.countAll());
        data.put("faultCount", faultReportMapper.countAll());
        data.put("chargeTrend", Arrays.asList(12, 19, 26, 33, 41, 52, 68));
        data.put("pilePie", Arrays.asList(map("可用", 36), map("占用", 28), map("维修", 22), map("离线", 14)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
