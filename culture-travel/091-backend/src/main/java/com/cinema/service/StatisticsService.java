package com.cinema.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cinema.entity.Payment;
import com.cinema.entity.TicketOrder;
import com.cinema.entity.User;
import com.cinema.mapper.MovieMapper;
import com.cinema.mapper.PaymentMapper;
import com.cinema.mapper.UserMapper;
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

    @Resource
    private PaymentMapper paymentMapper;

    public Map<String, Object> dashboard() {
        List<TicketOrder> paidOrders = orderService.listPaidOrders();
        LocalDate today = LocalDate.now();
        BigDecimal todaySales = paidOrders.stream()
                .filter(o -> o.getPayTime() != null && today.equals(o.getPayTime().toLocalDate()))
                .map(o -> o.getPayAmount() == null ? BigDecimal.ZERO : o.getPayAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        List<Payment> rechargeList = paymentMapper.selectList(new LambdaQueryWrapper<Payment>()
                .eq(Payment::getPayType, "RECHARGE")
                .eq(Payment::getStatus, "SUCCESS"));
        BigDecimal todayRecharge = rechargeList.stream()
                .filter(o -> o.getPayTime() != null && today.equals(o.getPayTime().toLocalDate()))
                .map(o -> o.getPayAmount() == null ? BigDecimal.ZERO : o.getPayAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalRecharge = rechargeList.stream()
                .map(o -> o.getPayAmount() == null ? BigDecimal.ZERO : o.getPayAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, Object> map = new HashMap<>();
        map.put("todaySales", todaySales);
        map.put("todayRecharge", todayRecharge);
        map.put("todayOrders", orderService.countTodayOrders());
        map.put("totalOrders", orderService.countAll());
        map.put("totalMembers", userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getRole, "MEMBER")));
        map.put("totalMovies", movieMapper.selectCount(null));
        map.put("totalRecharge", totalRecharge);
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
