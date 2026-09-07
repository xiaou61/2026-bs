package com.groupbuy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.groupbuy.entity.Merchant;
import com.groupbuy.entity.Orders;
import com.groupbuy.entity.Product;
import com.groupbuy.entity.User;
import com.groupbuy.mapper.MerchantMapper;
import com.groupbuy.mapper.OrdersMapper;
import com.groupbuy.mapper.ProductMapper;
import com.groupbuy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private ProductMapper productMapper;

    public Map<String, Object> overview() {
        Map<String, Object> result = new HashMap<>();
        result.put("userCount", userMapper.selectCount(new QueryWrapper<User>().eq("role", 2)));
        result.put("merchantCount", merchantMapper.selectCount(new QueryWrapper<Merchant>().eq("status", 1)));
        QueryWrapper<Orders> orderWrapper = new QueryWrapper<>();
        orderWrapper.in("status", 1, 2, 3);
        result.put("orderCount", ordersMapper.selectCount(orderWrapper));
        List<Orders> orders = ordersMapper.selectList(orderWrapper);
        BigDecimal totalAmount = orders.stream()
                .map(Orders::getPayAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        result.put("totalAmount", totalAmount);
        return result;
    }

    public List<Map<String, Object>> sales(Integer days) {
        List<Map<String, Object>> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = days - 1; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.atTime(LocalTime.MAX);
            QueryWrapper<Orders> wrapper = new QueryWrapper<>();
            wrapper.in("status", 1, 2, 3).ge("create_time", start).le("create_time", end);
            List<Orders> orders = ordersMapper.selectList(wrapper);
            Map<String, Object> item = new HashMap<>();
            item.put("date", date.toString());
            item.put("count", orders.size());
            BigDecimal amount = orders.stream()
                    .map(Orders::getPayAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            item.put("amount", amount);
            result.add(item);
        }
        return result;
    }

    public List<Map<String, Object>> hotProducts(Integer limit) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1).orderByDesc("sales").last("LIMIT " + limit);
        List<Product> products = productMapper.selectList(wrapper);
        return products.stream().map(p -> {
            Map<String, Object> item = new HashMap<>();
            item.put("id", p.getId());
            item.put("name", p.getName());
            item.put("cover", p.getCover());
            item.put("sales", p.getSales());
            item.put("price", p.getOriginalPrice());
            return item;
        }).collect(Collectors.toList());
    }

    public Map<String, Object> merchantStats(Long merchantId, Integer days) {
        Map<String, Object> result = new HashMap<>();
        LocalDateTime start = LocalDate.now().minusDays(days).atStartOfDay();
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", merchantId).in("status", 1, 2, 3).ge("create_time", start);
        List<Orders> orders = ordersMapper.selectList(wrapper);
        result.put("orderCount", orders.size());
        BigDecimal totalAmount = orders.stream()
                .map(Orders::getPayAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        result.put("totalAmount", totalAmount);
        QueryWrapper<Product> productWrapper = new QueryWrapper<>();
        productWrapper.eq("merchant_id", merchantId);
        result.put("productCount", productMapper.selectCount(productWrapper));
        List<Map<String, Object>> dailyStats = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = days - 1; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            LocalDateTime dayStart = date.atStartOfDay();
            LocalDateTime dayEnd = date.atTime(LocalTime.MAX);
            long count = orders.stream()
                    .filter(o -> !o.getCreateTime().isBefore(dayStart) && !o.getCreateTime().isAfter(dayEnd))
                    .count();
            BigDecimal amount = orders.stream()
                    .filter(o -> !o.getCreateTime().isBefore(dayStart) && !o.getCreateTime().isAfter(dayEnd))
                    .map(Orders::getPayAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            Map<String, Object> item = new HashMap<>();
            item.put("date", date.toString());
            item.put("count", count);
            item.put("amount", amount);
            dailyStats.add(item);
        }
        result.put("dailyStats", dailyStats);
        return result;
    }
}
