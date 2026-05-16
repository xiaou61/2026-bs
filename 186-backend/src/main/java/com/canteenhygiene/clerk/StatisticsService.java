package com.canteenhygiene.clerk;

import com.canteenhygiene.mapper.CanteenInfoMapper;
import com.canteenhygiene.mapper.KitchenAreaMapper;
import com.canteenhygiene.mapper.DishMenuMapper;
import com.canteenhygiene.mapper.SampleRetentionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final CanteenInfoMapper canteenInfoMapper;
    private final KitchenAreaMapper kitchenAreaMapper;
    private final DishMenuMapper dishMenuMapper;
    private final SampleRetentionMapper sampleRetentionMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("canteenCount", canteenInfoMapper.countAll());
        data.put("areaCount", kitchenAreaMapper.countAll());
        data.put("dishCount", dishMenuMapper.countAll());
        data.put("sampleCount", sampleRetentionMapper.countAll());
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
