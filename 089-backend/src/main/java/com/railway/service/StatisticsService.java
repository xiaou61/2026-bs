package com.railway.service;

import com.railway.entity.TicketOrder;
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
    private ScheduleService scheduleService;

    public Map<String, Object> dashboard() {
        List<TicketOrder> paidOrders = orderService.listPaidOrders();
        LocalDate today = LocalDate.now();
        BigDecimal todaySales = paidOrders.stream()
                .filter(order -> order.getPayTime() != null && today.equals(order.getPayTime().toLocalDate()))
                .map(order -> order.getPayAmount() == null ? BigDecimal.ZERO : order.getPayAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, Object> map = new HashMap<>();
        map.put("todaySales", todaySales);
        map.put("todayOrders", orderService.countTodayOrders());
        map.put("totalOrders", orderService.countAll());
        map.put("totalUsers", userService.countAll());
        map.put("totalSchedules", scheduleService.countAll());
        return map;
    }

    public List<Map<String, Object>> salesTrend(Integer days) {
        int size = days == null || days <= 0 ? 7 : days;
        List<TicketOrder> paidOrders = orderService.listPaidOrders();
        Map<LocalDate, List<TicketOrder>> dateMap = paidOrders.stream()
                .filter(order -> order.getPayTime() != null)
                .collect(Collectors.groupingBy(order -> order.getPayTime().toLocalDate()));
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = size - 1; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            List<TicketOrder> orders = dateMap.getOrDefault(date, new ArrayList<>());
            BigDecimal amount = orders.stream()
                    .map(order -> order.getPayAmount() == null ? BigDecimal.ZERO : order.getPayAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            Map<String, Object> item = new HashMap<>();
            item.put("date", date.toString());
            item.put("sales", amount);
            item.put("orders", orders.size());
            result.add(item);
        }
        return result;
    }

    public List<Map<String, Object>> hotTrain() {
        List<TicketOrder> paidOrders = orderService.listPaidOrders();
        Map<String, BigDecimal> sumMap = new HashMap<>();
        for (TicketOrder order : paidOrders) {
            String trainCode = order.getTrainCode() == null ? "未知车次" : order.getTrainCode();
            BigDecimal amount = order.getPayAmount() == null ? BigDecimal.ZERO : order.getPayAmount();
            sumMap.put(trainCode, sumMap.getOrDefault(trainCode, BigDecimal.ZERO).add(amount));
        }
        return sumMap.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("trainCode", entry.getKey());
                    item.put("sales", entry.getValue());
                    return item;
                })
                .sorted(Comparator.comparing((Map<String, Object> item) -> new BigDecimal(String.valueOf(item.get("sales")))).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
}
