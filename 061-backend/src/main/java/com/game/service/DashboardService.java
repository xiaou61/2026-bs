package com.game.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.game.entity.GameCategory;
import com.game.entity.GameItem;
import com.game.entity.TradeOrder;
import com.game.mapper.GameCategoryMapper;
import com.game.mapper.GameItemMapper;
import com.game.mapper.TradeOrderMapper;
import com.game.mapper.UserMapper;
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
    private GameItemMapper itemMapper;

    @Resource
    private TradeOrderMapper orderMapper;

    @Resource
    private GameCategoryMapper categoryMapper;

    public Map<String, Object> stats() {
        Map<String, Object> map = new HashMap<>();
        map.put("userCount", userMapper.selectCount(null));
        map.put("itemCount", itemMapper.selectCount(null));
        map.put("orderCount", orderMapper.selectCount(null));
        List<TradeOrder> paidOrders = orderMapper.selectList(new QueryWrapper<TradeOrder>().in("status", 1, 2, 3));
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (TradeOrder order : paidOrders) {
            if (order.getTotalPrice() != null) {
                totalAmount = totalAmount.add(order.getTotalPrice());
            }
        }
        map.put("totalAmount", totalAmount);
        return map;
    }

    public List<Map<String, Object>> categoryStats() {
        List<Map<String, Object>> data = new ArrayList<>();
        List<GameCategory> categories = categoryMapper.selectList(new QueryWrapper<GameCategory>().orderByAsc("sort", "id"));
        for (GameCategory category : categories) {
            Long count = itemMapper.selectCount(new QueryWrapper<GameItem>().eq("category_id", category.getId()));
            Map<String, Object> map = new HashMap<>();
            map.put("name", category.getName());
            map.put("value", count);
            data.add(map);
        }
        return data;
    }

    public List<Map<String, Object>> orderTrend() {
        List<Map<String, Object>> data = new ArrayList<>();
        List<TradeOrder> orders = orderMapper.selectList(new QueryWrapper<TradeOrder>()
                .ge("create_time", LocalDate.now().minusDays(6))
                .orderByAsc("create_time"));
        Map<String, Integer> counter = new LinkedHashMap<>();
        for (int i = 6; i >= 0; i--) {
            String key = LocalDate.now().minusDays(i).toString();
            counter.put(key, 0);
        }
        for (TradeOrder order : orders) {
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
        List<GameItem> items = itemMapper.selectList(new QueryWrapper<GameItem>()
                .eq("status", 1)
                .orderByDesc("sold_count")
                .last("limit 10"));
        List<Map<String, Object>> data = new ArrayList<>();
        for (GameItem item : items) {
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
