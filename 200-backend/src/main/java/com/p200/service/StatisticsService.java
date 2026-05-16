package com.p200.service;

import com.p200.mapper.BizRecord01Mapper;
import com.p200.mapper.BizRecord02Mapper;
import com.p200.mapper.BizRecord03Mapper;
import com.p200.mapper.BizRecord04Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final BizRecord01Mapper bizRecord01Mapper;
    private final BizRecord02Mapper bizRecord02Mapper;
    private final BizRecord03Mapper bizRecord03Mapper;
    private final BizRecord04Mapper bizRecord04Mapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("record01Count", bizRecord01Mapper.countAll());
        data.put("record02Count", bizRecord02Mapper.countAll());
        data.put("record03Count", bizRecord03Mapper.countAll());
        data.put("record04Count", bizRecord04Mapper.countAll());
        data.put("trend", Arrays.asList(18, 26, 23, 31, 37, 35, 42));
        data.put("pie", Arrays.asList(map("待处理", 24), map("处理中", 31), map("已完成", 45)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
