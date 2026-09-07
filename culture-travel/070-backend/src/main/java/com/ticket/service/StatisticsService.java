package com.ticket.service;

import com.ticket.entity.TicketOrder;
import com.ticket.mapper.MovieMapper;
import com.ticket.mapper.UserMapper;
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
    private UserMapper userMapper;

    @Resource
    private MovieMapper movieMapper;

    public Map<String, Object> dashboard() {
        List<TicketOrder> paidOrders = orderService.listPaidOrders();
        LocalDate today = LocalDate.now();
        BigDecimal todaySales = paidOrders.stream()
                .filter(o -> o.getPayTime() != null && today.equals(o.getPayTime().toLocalDate()))
                .map(o -> o.getPayAmount() == null ? BigDecimal.ZERO : o.getPayAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, Object> map = new HashMap<>();
        map.put("todaySales", todaySales);
        map.put("todayOrders", orderService.countTodayOrders());
        map.put("totalOrders", orderService.countAll());
        map.put("totalUsers", userMapper.selectCount(null));
        map.put("totalMovies", movieMapper.selectCount(null));
        return map;
    }

    public List<Map<String, Object>> salesTrend(Integer days) {
        int size = days == null || days <= 0 ? 7 : days;
        List<TicketOrder> paidOrders = orderService.listPaidOrders();
        Map<LocalDate, List<TicketOrder>> dateMap = paidOrders.stream()
                .filter(o -> o.getPayTime() != null)
                .collect(Collectors.groupingBy(o -> o.getPayTime().toLocalDate()));
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = size - 1; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            List<TicketOrder> orders = dateMap.getOrDefault(date, new ArrayList<>());
            BigDecimal amount = orders.stream()
                    .map(o -> o.getPayAmount() == null ? BigDecimal.ZERO : o.getPayAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            Map<String, Object> item = new HashMap<>();
            item.put("date", date.toString());
            item.put("sales", amount);
            item.put("orders", orders.size());
            result.add(item);
        }
        return result;
    }

    public List<Map<String, Object>> boxOffice() {
        List<TicketOrder> paidOrders = orderService.listPaidOrders();
        Map<String, BigDecimal> sumMap = new HashMap<>();
        for (TicketOrder order : paidOrders) {
            String title = order.getMovieTitle() == null ? "未知影片" : order.getMovieTitle();
            BigDecimal value = order.getPayAmount() == null ? BigDecimal.ZERO : order.getPayAmount();
            sumMap.put(title, sumMap.getOrDefault(title, BigDecimal.ZERO).add(value));
        }
        return sumMap.entrySet().stream()
                .map(e -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("movieTitle", e.getKey());
                    map.put("boxOffice", e.getValue());
                    return map;
                })
                .sorted(Comparator.comparing((Map<String, Object> m) -> new BigDecimal(String.valueOf(m.get("boxOffice")))).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
}
