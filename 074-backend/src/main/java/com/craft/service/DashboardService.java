package com.craft.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.craft.entity.CraftCategory;
import com.craft.entity.CraftItem;
import com.craft.entity.CraftOrder;
import com.craft.mapper.CraftCategoryMapper;
import com.craft.mapper.CraftItemMapper;
import com.craft.mapper.CraftOrderMapper;
import com.craft.mapper.UserMapper;
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
    private CraftItemMapper itemMapper;

    @Resource
    private CraftOrderMapper orderMapper;

    @Resource
    private CraftCategoryMapper categoryMapper;

    public Map<String, Object> stats() {
        Map<String, Object> map = new HashMap<>();
        map.put("userCount", userMapper.selectCount(null));
        map.put("itemCount", itemMapper.selectCount(null));
        map.put("orderCount", orderMapper.selectCount(null));
        List<CraftOrder> paidOrders = orderMapper.selectList(new QueryWrapper<CraftOrder>().in("status", 1, 2, 3));
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CraftOrder order : paidOrders) {
            if (order.getTotalPrice() != null) {
                totalAmount = totalAmount.add(order.getTotalPrice());
            }
        }
        map.put("totalAmount", totalAmount);
        return map;
    }

    public List<Map<String, Object>> categoryStats() {
        List<Map<String, Object>> data = new ArrayList<>();
        List<CraftCategory> categories = categoryMapper.selectList(new QueryWrapper<CraftCategory>().orderByAsc("sort", "id"));
        for (CraftCategory category : categories) {
            Long count = itemMapper.selectCount(new QueryWrapper<CraftItem>().eq("category_id", category.getId()));
            Map<String, Object> map = new HashMap<>();
            map.put("name", category.getName());
            map.put("value", count);
            data.add(map);
        }
        return data;
    }

    public List<Map<String, Object>> orderTrend() {
        List<Map<String, Object>> data = new ArrayList<>();
        List<CraftOrder> orders = orderMapper.selectList(new QueryWrapper<CraftOrder>()
                .ge("create_time", LocalDate.now().minusDays(6))
                .orderByAsc("create_time"));
        Map<String, Integer> counter = new LinkedHashMap<>();
        for (int i = 6; i >= 0; i--) {
            String key = LocalDate.now().minusDays(i).toString();
            counter.put(key, 0);
        }
        for (CraftOrder order : orders) {
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

    public List<Map<String, Object>> hotItems() {
        List<CraftItem> items = itemMapper.selectList(new QueryWrapper<CraftItem>()
                .eq("status", 1)
                .orderByDesc("sold_count")
                .last("limit 10"));
        List<Map<String, Object>> data = new ArrayList<>();
        for (CraftItem item : items) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("title", item.getTitle());
            map.put("soldCount", item.getSoldCount());
            map.put("price", item.getPrice());
            data.add(map);
        }
        return data;
    }
}

