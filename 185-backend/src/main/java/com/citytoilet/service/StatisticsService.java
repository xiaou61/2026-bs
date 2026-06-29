package com.citytoilet.service;

import com.citytoilet.mapper.ToiletSiteMapper;
import com.citytoilet.mapper.CleaningRouteMapper;
import com.citytoilet.mapper.CleaningTaskMapper;
import com.citytoilet.mapper.CleaningRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ToiletSiteMapper toiletSiteMapper;
    private final CleaningRouteMapper cleaningRouteMapper;
    private final CleaningTaskMapper cleaningTaskMapper;
    private final CleaningRecordMapper cleaningRecordMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("siteCount", toiletSiteMapper.countAll());
        data.put("routeCount", cleaningRouteMapper.countAll());
        data.put("taskCount", cleaningTaskMapper.countAll());
        data.put("recordCount", cleaningRecordMapper.countAll());
        data.put("trend", Arrays.asList(58, 74, 88, 105, 121, 137, 154));
        data.put("pie", Arrays.asList(map("已登记", 31), map("已下单", 44), map("备餐中", 19), map("已关闭", 68)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
