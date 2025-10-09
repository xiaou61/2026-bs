package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.common.BusinessException;
import com.xiaou.common.Result;
import com.xiaou.entity.*;
import com.xiaou.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/overview")
    public Result<?> overview(@RequestAttribute String userRole) {
        if (!"admin".equals(userRole)) {
            throw new BusinessException(403, "无权访问");
        }

        Map<String, Object> data = new HashMap<>();

        long totalUsers = userService.count();
        long totalProducts = productService.count();
        long totalOrders = orderService.count();

        LambdaQueryWrapper<User> farmerWrapper = new LambdaQueryWrapper<>();
        farmerWrapper.eq(User::getRole, "farmer");
        long totalFarmers = userService.count(farmerWrapper);

        LambdaQueryWrapper<User> buyerWrapper = new LambdaQueryWrapper<>();
        buyerWrapper.eq(User::getRole, "buyer");
        long totalBuyers = userService.count(buyerWrapper);

        LambdaQueryWrapper<Product> approvedWrapper = new LambdaQueryWrapper<>();
        approvedWrapper.eq(Product::getStatus, "approved");
        long approvedProducts = productService.count(approvedWrapper);

        LambdaQueryWrapper<Order> completedWrapper = new LambdaQueryWrapper<>();
        completedWrapper.eq(Order::getStatus, "completed");
        List<Order> completedOrders = orderService.list(completedWrapper);
        BigDecimal totalSales = completedOrders.stream()
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        data.put("totalUsers", totalUsers);
        data.put("totalFarmers", totalFarmers);
        data.put("totalBuyers", totalBuyers);
        data.put("totalProducts", totalProducts);
        data.put("approvedProducts", approvedProducts);
        data.put("totalOrders", totalOrders);
        data.put("totalSales", totalSales);

        return Result.success(data);
    }

    @GetMapping("/farmer-sales")
    public Result<?> farmerSales(@RequestAttribute String userRole) {
        if (!"admin".equals(userRole)) {
            throw new BusinessException(403, "无权访问");
        }

        List<OrderItem> allItems = orderItemService.list();
        Map<Long, BigDecimal> salesMap = new HashMap<>();

        for (OrderItem item : allItems) {
            Long farmerId = item.getFarmerId();
            BigDecimal amount = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
            salesMap.put(farmerId, salesMap.getOrDefault(farmerId, BigDecimal.ZERO).add(amount));
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<Long, BigDecimal> entry : salesMap.entrySet()) {
            User farmer = userService.getById(entry.getKey());
            if (farmer != null) {
                Map<String, Object> item = new HashMap<>();
                item.put("farmerId", entry.getKey());
                item.put("farmerName", farmer.getRealName());
                item.put("sales", entry.getValue());
                result.add(item);
            }
        }

        result.sort((a, b) -> ((BigDecimal) b.get("sales")).compareTo((BigDecimal) a.get("sales")));

        return Result.success(result);
    }
}

