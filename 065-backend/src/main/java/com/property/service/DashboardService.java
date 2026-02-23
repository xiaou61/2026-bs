package com.property.service;

import com.property.mapper.HouseMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    private UserService userService;

    @Resource
    private HouseMapper houseMapper;

    @Resource
    private FeeOrderService feeOrderService;

    @Resource
    private RepairOrderService repairOrderService;

    public Map<String, Object> stats() {
        Map<String, Object> map = new HashMap<>();
        map.put("userCount", userService.countAll());
        map.put("houseCount", houseMapper.countAll());
        map.put("unpaidFeeCount", feeOrderService.countUnpaid());
        map.put("pendingRepairCount", repairOrderService.countPending());
        map.put("todayPaidAmount", feeOrderService.todayPaidAmount());
        return map;
    }

    public Map<String, Object> trend() {
        LocalDate startDay = LocalDate.now().minusDays(6);
        LocalDateTime start = startDay.atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();
        List<Map<String, Object>> feeList = feeOrderService.dailyPaidAmount(start, end);
        Map<String, BigDecimal> feeMap = new HashMap<>();
        for (Map<String, Object> row : feeList) {
            feeMap.put(row.get("day").toString(), new BigDecimal(row.get("amount").toString()));
        }
        List<Map<String, Object>> daily = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            String day = LocalDate.now().minusDays(i).toString();
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("day", day);
            item.put("paidAmount", feeMap.getOrDefault(day, BigDecimal.ZERO));
            daily.add(item);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("daily", daily);
        result.put("repairStatus", repairOrderService.statusStats());
        return result;
    }
}
