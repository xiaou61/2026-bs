package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.Express;
import com.xiaou.service.ExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin
public class StatsController {

    @Autowired
    private ExpressService expressService;

    @GetMapping("/overview")
    public Result<?> overview() {
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        QueryWrapper<Express> todayInWrapper = new QueryWrapper<>();
        todayInWrapper.between("in_time", todayStart, todayEnd);
        long todayIn = expressService.count(todayInWrapper);

        QueryWrapper<Express> todayOutWrapper = new QueryWrapper<>();
        todayOutWrapper.between("out_time", todayStart, todayEnd);
        long todayOut = expressService.count(todayOutWrapper);

        QueryWrapper<Express> stockWrapper = new QueryWrapper<>();
        stockWrapper.eq("status", 0);
        long currentStock = expressService.count(stockWrapper);

        QueryWrapper<Express> overdueWrapper = new QueryWrapper<>();
        overdueWrapper.eq("status", 0);
        List<Express> expressList = expressService.list(overdueWrapper);
        long overdueCount = expressList.stream()
                .filter(e -> e.getInTime().isBefore(LocalDateTime.now().minusDays(3)))
                .count();

        BigDecimal overdueFeeTotal = expressList.stream()
                .map(Express::getOverdueFee)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> data = new HashMap<>();
        data.put("todayIn", todayIn);
        data.put("todayOut", todayOut);
        data.put("currentStock", currentStock);
        data.put("overdueCount", overdueCount);
        data.put("overdueFeeTotal", overdueFeeTotal);

        return Result.success(data);
    }

    @GetMapping("/trend")
    public Result<?> trend(@RequestParam(defaultValue = "7") Integer days) {
        List<Map<String, Object>> trendData = new ArrayList<>();
        
        for (int i = days - 1; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);

            QueryWrapper<Express> inWrapper = new QueryWrapper<>();
            inWrapper.between("in_time", start, end);
            long inCount = expressService.count(inWrapper);

            QueryWrapper<Express> outWrapper = new QueryWrapper<>();
            outWrapper.between("out_time", start, end);
            long outCount = expressService.count(outWrapper);

            Map<String, Object> dayData = new HashMap<>();
            dayData.put("date", date.toString());
            dayData.put("inCount", inCount);
            dayData.put("outCount", outCount);
            trendData.add(dayData);
        }

        return Result.success(trendData);
    }

    @GetMapping("/company-rank")
    public Result<?> companyRank() {
        List<Express> allExpress = expressService.list();
        
        Map<String, Long> companyCount = allExpress.stream()
                .collect(Collectors.groupingBy(Express::getExpressCompany, Collectors.counting()));

        List<Map<String, Object>> rankData = companyCount.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("company", entry.getKey());
                    item.put("count", entry.getValue());
                    return item;
                })
                .sorted((a, b) -> ((Long) b.get("count")).compareTo((Long) a.get("count")))
                .limit(10)
                .collect(Collectors.toList());

        return Result.success(rankData);
    }

    @GetMapping("/station-rank")
    public Result<?> stationRank() {
        List<Express> allExpress = expressService.list();
        
        Map<Long, Long> stationCount = allExpress.stream()
                .collect(Collectors.groupingBy(Express::getStationId, Collectors.counting()));

        List<Map<String, Object>> rankData = stationCount.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("stationId", entry.getKey());
                    item.put("count", entry.getValue());
                    return item;
                })
                .sorted((a, b) -> ((Long) b.get("count")).compareTo((Long) a.get("count")))
                .collect(Collectors.toList());

        return Result.success(rankData);
    }
}

