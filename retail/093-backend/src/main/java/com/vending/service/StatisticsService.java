package com.vending.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.vending.entity.FaultReport;
import com.vending.entity.PaymentRecord;
import com.vending.entity.ReplenishRecord;
import com.vending.entity.SaleOrder;
import com.vending.mapper.FaultReportMapper;
import com.vending.mapper.ReplenishRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    @Resource
    private OrderService orderService;

    @Resource
    private UserService userService;

    @Resource
    private MachineService machineService;

    @Resource
    private ProductService productService;

    @Resource
    private PaymentService paymentService;

    @Resource
    private FaultReportMapper faultMapper;

    @Resource
    private ReplenishRecordMapper replenishMapper;

    public Map<String, Object> dashboard() {
        List<SaleOrder> orders = orderService.listFinishedOrders();
        LocalDate today = LocalDate.now();
        BigDecimal todaySales = orders.stream()
                .filter(item -> item.getPayTime() != null && today.equals(item.getPayTime().toLocalDate()))
                .map(item -> item.getPayAmount() == null ? BigDecimal.ZERO : item.getPayAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Long pendingFaults = faultMapper.selectCount(new LambdaQueryWrapper<FaultReport>().eq(FaultReport::getHandleStatus, "PENDING"));
        Long todayReplenish = replenishMapper.selectCount(new LambdaQueryWrapper<ReplenishRecord>()
                .ge(ReplenishRecord::getCreateTime, today.atStartOfDay())
                .lt(ReplenishRecord::getCreateTime, today.plusDays(1).atStartOfDay()));
        Map<String, Object> map = new HashMap<>();
        map.put("todaySales", todaySales);
        map.put("todayOrders", orderService.countTodayOrders());
        map.put("totalOrders", orderService.countAll());
        map.put("totalCustomers", userService.countCustomers());
        map.put("totalMachines", machineService.countAll());
        map.put("totalProducts", productService.countAll());
        map.put("pendingFaults", pendingFaults);
        map.put("todayReplenish", todayReplenish);
        return map;
    }

    public List<Map<String, Object>> salesTrend(Integer days) {
        int size = days == null || days <= 0 ? 7 : days;
        List<SaleOrder> orders = orderService.listFinishedOrders();
        Map<LocalDate, List<SaleOrder>> dateMap = orders.stream()
                .filter(item -> item.getPayTime() != null)
                .collect(Collectors.groupingBy(item -> item.getPayTime().toLocalDate()));
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = size - 1; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            List<SaleOrder> dayList = dateMap.getOrDefault(date, new ArrayList<>());
            BigDecimal amount = dayList.stream()
                    .map(item -> item.getPayAmount() == null ? BigDecimal.ZERO : item.getPayAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            Map<String, Object> item = new HashMap<>();
            item.put("date", date.toString());
            item.put("sales", amount);
            item.put("orders", dayList.size());
            result.add(item);
        }
        return result;
    }

    public List<Map<String, Object>> hotProducts() {
        List<SaleOrder> orders = orderService.listFinishedOrders();
        Map<String, BigDecimal> amountMap = new HashMap<>();
        Map<String, Integer> quantityMap = new HashMap<>();
        for (SaleOrder order : orders) {
            String key = order.getProductName() == null ? "未知商品" : order.getProductName();
            BigDecimal amount = order.getPayAmount() == null ? BigDecimal.ZERO : order.getPayAmount();
            amountMap.put(key, amountMap.getOrDefault(key, BigDecimal.ZERO).add(amount));
            quantityMap.put(key, quantityMap.getOrDefault(key, 0) + (order.getQuantity() == null ? 0 : order.getQuantity()));
        }
        return amountMap.entrySet().stream()
                .map(item -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("productName", item.getKey());
                    map.put("salesAmount", item.getValue());
                    map.put("quantity", quantityMap.getOrDefault(item.getKey(), 0));
                    return map;
                })
                .sorted(Comparator.comparing((Map<String, Object> item) -> new BigDecimal(String.valueOf(item.get("salesAmount")))).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> machineRank() {
        List<SaleOrder> orders = orderService.listFinishedOrders();
        Map<String, BigDecimal> amountMap = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();
        for (SaleOrder order : orders) {
            String key = order.getMachineName() == null ? "未知设备" : order.getMachineName();
            BigDecimal amount = order.getPayAmount() == null ? BigDecimal.ZERO : order.getPayAmount();
            amountMap.put(key, amountMap.getOrDefault(key, BigDecimal.ZERO).add(amount));
            countMap.put(key, countMap.getOrDefault(key, 0) + 1);
        }
        return amountMap.entrySet().stream()
                .map(item -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("machineName", item.getKey());
                    map.put("salesAmount", item.getValue());
                    map.put("orderCount", countMap.getOrDefault(item.getKey(), 0));
                    return map;
                })
                .sorted(Comparator.comparing((Map<String, Object> item) -> new BigDecimal(String.valueOf(item.get("salesAmount")))).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public BigDecimal totalRechargeAmount() {
        return paymentService.listSuccessRecords().stream()
                .filter(item -> "RECHARGE".equals(item.getPayType()))
                .map(PaymentRecord::getPayAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
