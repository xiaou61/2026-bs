package com.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ticket.entity.Movie;
import com.ticket.entity.Order;
import com.ticket.entity.User;
import com.ticket.mapper.MovieMapper;
import com.ticket.mapper.OrderMapper;
import com.ticket.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MovieMapper movieMapper;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        QueryWrapper<Order> todayOrders = new QueryWrapper<>();
        todayOrders.eq("status", "paid")
                .between("pay_time", todayStart, todayEnd);
        List<Order> orders = orderMapper.selectList(todayOrders);

        BigDecimal todaySales = orders.stream()
                .map(Order::getPayAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        stats.put("todaySales", todaySales);
        stats.put("todayOrders", orders.size());
        stats.put("totalUsers", userMapper.selectCount(null));
        stats.put("totalMovies", movieMapper.selectCount(new QueryWrapper<Movie>().eq("status", "showing")));

        return stats;
    }

    public List<Map<String, Object>> getSalesTrend(Integer days) {
        List<Map<String, Object>> trend = new ArrayList<>();

        for (int i = days - 1; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);

            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("status", "paid")
                    .between("pay_time", start, end);
            List<Order> orders = orderMapper.selectList(wrapper);

            BigDecimal amount = orders.stream()
                    .map(Order::getPayAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Map<String, Object> item = new HashMap<>();
            item.put("date", date.toString());
            item.put("amount", amount);
            item.put("count", orders.size());
            trend.add(item);
        }

        return trend;
    }

    public List<Map<String, Object>> getBoxOfficeRank() {
        List<Map<String, Object>> rank = new ArrayList<>();

        QueryWrapper<Movie> wrapper = new QueryWrapper<>();
        wrapper.eq("status", "showing")
                .orderByDesc("rating")
                .last("LIMIT 10");
        List<Movie> movies = movieMapper.selectList(wrapper);

        for (Movie movie : movies) {
            QueryWrapper<Order> orderWrapper = new QueryWrapper<>();
            orderWrapper.eq("movie_title", movie.getTitle())
                    .eq("status", "paid");
            List<Order> orders = orderMapper.selectList(orderWrapper);

            BigDecimal boxOffice = orders.stream()
                    .map(Order::getPayAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Map<String, Object> item = new HashMap<>();
            item.put("movieTitle", movie.getTitle());
            item.put("boxOffice", boxOffice);
            item.put("orderCount", orders.size());
            item.put("rating", movie.getRating());
            rank.add(item);
        }

        return rank;
    }
}
