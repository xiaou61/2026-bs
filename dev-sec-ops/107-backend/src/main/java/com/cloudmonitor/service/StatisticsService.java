package com.cloudmonitor.service;

import com.cloudmonitor.mapper.AlertEventMapper;
import com.cloudmonitor.mapper.AlertRuleMapper;
import com.cloudmonitor.mapper.IncidentTicketMapper;
import com.cloudmonitor.mapper.MetricSampleMapper;
import com.cloudmonitor.mapper.ServerAssetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ServerAssetMapper assetMapper;
    private final MetricSampleMapper sampleMapper;
    private final AlertRuleMapper ruleMapper;
    private final AlertEventMapper eventMapper;
    private final IncidentTicketMapper ticketMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("assetCount", assetMapper.selectCount(null));
        data.put("sampleCount", sampleMapper.selectCount(null));
        data.put("ruleCount", ruleMapper.selectCount(null));
        data.put("eventCount", eventMapper.selectCount(null));
        data.put("ticketCount", ticketMapper.selectCount(null));
        data.put("metricTrend", Arrays.asList(55, 62, 59, 71, 68, 73, 64));
        data.put("severityPie", Arrays.asList(map("严重", 4), map("高", 8), map("中", 15), map("低", 22)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
