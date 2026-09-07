package com.repair.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.repair.entity.Evaluate;
import com.repair.entity.RepairOrder;
import com.repair.entity.Technician;
import com.repair.mapper.EvaluateMapper;
import com.repair.mapper.RepairOrderMapper;
import com.repair.mapper.TechnicianMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class StatisticsService {

    @Autowired
    private RepairOrderMapper repairOrderMapper;

    @Autowired
    private TechnicianMapper technicianMapper;

    @Autowired
    private EvaluateMapper evaluateMapper;

    public Map<String, Object> getDashboard() {
        Map<String, Object> result = new HashMap<>();

        long totalOrders = repairOrderMapper.selectCount(null);

        QueryWrapper<RepairOrder> pendingWrapper = new QueryWrapper<>();
        pendingWrapper.eq("status", 0);
        long pendingOrders = repairOrderMapper.selectCount(pendingWrapper);

        QueryWrapper<RepairOrder> processingWrapper = new QueryWrapper<>();
        processingWrapper.in("status", 1, 2, 3);
        long processingOrders = repairOrderMapper.selectCount(processingWrapper);

        QueryWrapper<RepairOrder> completedWrapper = new QueryWrapper<>();
        completedWrapper.eq("status", 4);
        long completedOrders = repairOrderMapper.selectCount(completedWrapper);

        long totalTechnicians = technicianMapper.selectCount(null);

        QueryWrapper<Technician> activeWrapper = new QueryWrapper<>();
        activeWrapper.eq("work_status", 1);
        long activeTechnicians = technicianMapper.selectCount(activeWrapper);

        List<Evaluate> evaluateList = evaluateMapper.selectList(null);
        BigDecimal avgScore = BigDecimal.ZERO;
        if (!evaluateList.isEmpty()) {
            int scoreSum = evaluateList.stream().mapToInt(item -> item.getScore() == null ? 0 : item.getScore()).sum();
            avgScore = BigDecimal.valueOf(scoreSum)
                    .divide(BigDecimal.valueOf(evaluateList.size()), 2, RoundingMode.HALF_UP);
        }

        result.put("totalOrders", totalOrders);
        result.put("pendingOrders", pendingOrders);
        result.put("processingOrders", processingOrders);
        result.put("completedOrders", completedOrders);
        result.put("totalTechnicians", totalTechnicians);
        result.put("activeTechnicians", activeTechnicians);
        result.put("avgScore", avgScore);

        return result;
    }

    public Map<String, Object> getOrderTrend(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            endDate = LocalDate.now();
            startDate = endDate.minusDays(6);
        }
        QueryWrapper<RepairOrder> wrapper = new QueryWrapper<>();
        wrapper.ge("create_time", startDate.atStartOfDay());
        wrapper.le("create_time", endDate.plusDays(1).atStartOfDay());
        List<RepairOrder> orders = repairOrderMapper.selectList(wrapper);

        TreeMap<String, Integer> dayCountMap = new TreeMap<>();
        LocalDate cursor = startDate;
        while (!cursor.isAfter(endDate)) {
            dayCountMap.put(cursor.toString(), 0);
            cursor = cursor.plusDays(1);
        }

        for (RepairOrder order : orders) {
            LocalDateTime createTime = order.getCreateTime();
            if (createTime == null) {
                continue;
            }
            String key = createTime.toLocalDate().toString();
            if (dayCountMap.containsKey(key)) {
                dayCountMap.put(key, dayCountMap.get(key) + 1);
            }
        }

        List<String> dateList = new ArrayList<>(dayCountMap.keySet());
        List<Integer> countList = new ArrayList<>(dayCountMap.values());

        Map<String, Object> result = new HashMap<>();
        result.put("dateList", dateList);
        result.put("countList", countList);
        return result;
    }
}
