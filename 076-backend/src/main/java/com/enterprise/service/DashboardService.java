package com.enterprise.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.enterprise.entity.RepairCategory;
import com.enterprise.entity.RepairService;
import com.enterprise.entity.RepairAppointment;
import com.enterprise.mapper.RepairCategoryMapper;
import com.enterprise.mapper.RepairServiceMapper;
import com.enterprise.mapper.RepairAppointmentMapper;
import com.enterprise.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    private RepairServiceMapper serviceMapper;

    @Resource
    private RepairAppointmentMapper orderMapper;

    @Resource
    private RepairCategoryMapper categoryMapper;

    public Map<String, Object> stats() {
        Map<String, Object> map = new HashMap<>();
        map.put("userCount", userMapper.selectCount(null));
        map.put("serviceCount", serviceMapper.selectCount(null));
        map.put("orderCount", orderMapper.selectCount(null));
        List<RepairAppointment> paidOrders = orderMapper.selectList(new QueryWrapper<RepairAppointment>().in("status", 1, 2, 3));
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (RepairAppointment order : paidOrders) {
            if (order.getTotalPrice() != null) {
                totalAmount = totalAmount.add(order.getTotalPrice());
            }
        }
        map.put("totalAmount", totalAmount);
        return map;
    }

    public List<Map<String, Object>> categoryStats() {
        List<Map<String, Object>> data = new ArrayList<>();
        List<RepairCategory> categories = categoryMapper.selectList(new QueryWrapper<RepairCategory>().orderByAsc("sort", "id"));
        for (RepairCategory category : categories) {
            Long count = serviceMapper.selectCount(new QueryWrapper<RepairService>().eq("category_id", category.getId()));
            Map<String, Object> map = new HashMap<>();
            map.put("name", category.getName());
            map.put("value", count);
            data.add(map);
        }
        return data;
    }

    public List<Map<String, Object>> orderTrend() {
        List<Map<String, Object>> data = new ArrayList<>();
        List<RepairAppointment> orders = orderMapper.selectList(new QueryWrapper<RepairAppointment>()
                .ge("create_time", LocalDate.now().minusDays(6))
                .orderByAsc("create_time"));
        Map<String, Integer> counter = new LinkedHashMap<>();
        for (int i = 6; i >= 0; i--) {
            String key = LocalDate.now().minusDays(i).toString();
            counter.put(key, 0);
        }
        for (RepairAppointment order : orders) {
            if (order.getCreateTime() != null) {
                String day = order.getCreateTime().toLocalDate().toString();
                counter.put(day, counter.getOrDefault(day, 0) + 1);
            }
        }
        for (Map.Entry<String, Integer> entry : counter.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("date", entry.getKey());
            map.put("count", entry.getValue());
            data.add(map);
        }
        return data;
    }

    public List<Map<String, Object>> hotServices() {
        List<RepairService> services = serviceMapper.selectList(new QueryWrapper<RepairService>()
                .eq("status", 1)
                .orderByDesc("booked_count")
                .last("limit 10"));
        List<Map<String, Object>> data = new ArrayList<>();
        for (RepairService service : services) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", service.getId());
            map.put("title", service.getTitle());
            map.put("bookedCount", service.getBookedCount());
            map.put("price", service.getPrice());
            data.add(map);
        }
        return data;
    }
}





