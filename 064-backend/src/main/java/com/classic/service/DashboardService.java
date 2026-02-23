package com.classic.service;

import com.classic.entity.ServiceOrder;
import com.classic.mapper.ConstitutionRecordMapper;
import com.classic.mapper.FormulaInfoMapper;
import com.classic.mapper.IngredientMapper;
import com.classic.mapper.MealPlanMapper;
import com.classic.mapper.ServiceOrderMapper;
import com.classic.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private IngredientMapper ingredientMapper;

    @Resource
    private FormulaInfoMapper formulaInfoMapper;

    @Resource
    private MealPlanMapper mealPlanMapper;

    @Resource
    private ServiceOrderMapper serviceOrderMapper;

    @Resource
    private ConstitutionRecordMapper constitutionRecordMapper;

    public Map<String, Object> stats() {
        Map<String, Object> map = new HashMap<>();
        map.put("userCount", userMapper.selectCount(null));
        map.put("ingredientCount", ingredientMapper.selectCount(null));
        map.put("formulaCount", formulaInfoMapper.selectCount(null));
        map.put("planCount", mealPlanMapper.selectCount(null));
        map.put("orderCount", serviceOrderMapper.selectCount(null));
        map.put("pendingOrderCount", serviceOrderMapper.selectCount(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<ServiceOrder>().eq(ServiceOrder::getStatus, 0)));
        map.put("repliedConstitutionCount", constitutionRecordMapper.selectCount(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<com.classic.entity.ConstitutionRecord>().eq(com.classic.entity.ConstitutionRecord::getStatus, 1)));
        return map;
    }

    public Map<String, Object> orderTrend() {
        LocalDate startDay = LocalDate.now().minusDays(6);
        LocalDateTime start = startDay.atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();
        List<Map<String, Object>> rawDaily = serviceOrderMapper.dailyCount(start, end);
        Map<String, Long> dailyMap = new HashMap<>();
        for (Map<String, Object> row : rawDaily) {
            dailyMap.put(row.get("day").toString(), Long.parseLong(row.get("total").toString()));
        }
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> daily = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            String day = LocalDate.now().minusDays(i).toString();
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("day", day);
            item.put("total", dailyMap.getOrDefault(day, 0L));
            daily.add(item);
        }
        result.put("daily", daily);
        result.put("status", serviceOrderMapper.statusStats());
        return result;
    }
}
