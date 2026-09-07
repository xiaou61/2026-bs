package com.petcafe.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.petcafe.entity.ConsumeOrder;
import com.petcafe.entity.PaymentRecord;
import com.petcafe.entity.ReservationRecord;
import com.petcafe.entity.ReviewRecord;
import com.petcafe.mapper.ReservationRecordMapper;
import com.petcafe.mapper.ReviewRecordMapper;
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
    private ShopService shopService;

    @Resource
    private PetService petService;

    @Resource
    private MenuService menuService;

    @Resource
    private PaymentService paymentService;

    @Resource
    private ReviewRecordMapper reviewMapper;

    @Resource
    private ReservationRecordMapper reservationMapper;

    public Map<String, Object> dashboard() {
        List<ConsumeOrder> orders = orderService.listFinishedOrders();
        LocalDate today = LocalDate.now();
        BigDecimal todaySales = orders.stream()
                .filter(item -> item.getPayTime() != null && today.equals(item.getPayTime().toLocalDate()))
                .map(item -> item.getPayAmount() == null ? BigDecimal.ZERO : item.getPayAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Long pendingReviews = reviewMapper.selectCount(new LambdaQueryWrapper<ReviewRecord>().eq(ReviewRecord::getReviewStatus, "WAIT_REPLY"));
        Long todayReservations = reservationMapper.selectCount(new LambdaQueryWrapper<ReservationRecord>()
                .ge(ReservationRecord::getCreateTime, today.atStartOfDay())
                .lt(ReservationRecord::getCreateTime, today.plusDays(1).atStartOfDay()));
        Map<String, Object> map = new HashMap<>();
        map.put("todaySales", todaySales);
        map.put("todayOrders", orderService.countTodayOrders());
        map.put("totalOrders", orderService.countAll());
        map.put("totalCustomers", userService.countCustomers());
        map.put("totalShops", shopService.countAll());
        map.put("totalPets", petService.countAll());
        map.put("totalMenus", menuService.countAll());
        map.put("pendingReviews", pendingReviews);
        map.put("todayReservations", todayReservations);
        return map;
    }

    public List<Map<String, Object>> salesTrend(Integer days) {
        int size = days == null || days <= 0 ? 7 : days;
        List<ConsumeOrder> orders = orderService.listFinishedOrders();
        Map<LocalDate, List<ConsumeOrder>> dateMap = orders.stream()
                .filter(item -> item.getPayTime() != null)
                .collect(Collectors.groupingBy(item -> item.getPayTime().toLocalDate()));
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = size - 1; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            List<ConsumeOrder> dayList = dateMap.getOrDefault(date, new ArrayList<>());
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

    public List<Map<String, Object>> hotMenus() {
        List<ConsumeOrder> orders = orderService.listFinishedOrders();
        Map<String, BigDecimal> amountMap = new HashMap<>();
        Map<String, Integer> quantityMap = new HashMap<>();
        for (ConsumeOrder order : orders) {
            String key = order.getMenuName() == null ? "未知菜单" : order.getMenuName();
            BigDecimal amount = order.getPayAmount() == null ? BigDecimal.ZERO : order.getPayAmount();
            amountMap.put(key, amountMap.getOrDefault(key, BigDecimal.ZERO).add(amount));
            quantityMap.put(key, quantityMap.getOrDefault(key, 0) + (order.getQuantity() == null ? 0 : order.getQuantity()));
        }
        return amountMap.entrySet().stream()
                .map(item -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("menuName", item.getKey());
                    map.put("salesAmount", item.getValue());
                    map.put("quantity", quantityMap.getOrDefault(item.getKey(), 0));
                    return map;
                })
                .sorted(Comparator.comparing((Map<String, Object> item) -> new BigDecimal(String.valueOf(item.get("salesAmount")))).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> shopRank() {
        List<ConsumeOrder> orders = orderService.listFinishedOrders();
        Map<String, BigDecimal> amountMap = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();
        for (ConsumeOrder order : orders) {
            String key = order.getShopName() == null ? "未知门店" : order.getShopName();
            BigDecimal amount = order.getPayAmount() == null ? BigDecimal.ZERO : order.getPayAmount();
            amountMap.put(key, amountMap.getOrDefault(key, BigDecimal.ZERO).add(amount));
            countMap.put(key, countMap.getOrDefault(key, 0) + 1);
        }
        return amountMap.entrySet().stream()
                .map(item -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("shopName", item.getKey());
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
