package com.eldermeal.clerk;

import com.eldermeal.mapper.MealSiteMapper;
import com.eldermeal.mapper.ElderProfileMapper;
import com.eldermeal.mapper.NutritionMenuMapper;
import com.eldermeal.mapper.MealOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final MealSiteMapper mealSiteMapper;
    private final ElderProfileMapper elderProfileMapper;
    private final NutritionMenuMapper nutritionMenuMapper;
    private final MealOrderMapper mealOrderMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("siteCount", mealSiteMapper.countAll());
        data.put("elderCount", elderProfileMapper.countAll());
        data.put("menuCount", nutritionMenuMapper.countAll());
        data.put("orderCount", mealOrderMapper.countAll());
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
