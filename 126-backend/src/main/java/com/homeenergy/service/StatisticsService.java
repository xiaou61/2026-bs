package com.homeenergy.service;

import com.homeenergy.mapper.HomeProfileMapper;
import com.homeenergy.mapper.ElectricDeviceMapper;
import com.homeenergy.mapper.ElectricityBillMapper;
import com.homeenergy.mapper.SavingSuggestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final HomeProfileMapper homeProfileMapper;
    private final ElectricDeviceMapper electricDeviceMapper;
    private final ElectricityBillMapper electricityBillMapper;
    private final SavingSuggestionMapper savingSuggestionMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("homeCount", homeProfileMapper.countAll());
        data.put("deviceCount", electricDeviceMapper.countAll());
        data.put("billCount", electricityBillMapper.countAll());
        data.put("suggestionCount", savingSuggestionMapper.countAll());
        data.put("energyTrend", Arrays.asList(12, 19, 26, 33, 41, 52, 68));
        data.put("devicePie", Arrays.asList(map("空调", 36), map("热水器", 28), map("照明", 22), map("厨房电器", 14)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
